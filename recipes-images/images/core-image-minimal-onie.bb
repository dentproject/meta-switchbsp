SUMMARY = "A minimal ONIE installer image"
DESCRIPTION = "This package is based on top of core-image-minimal and \
    creates a ONIE installer image from the rootfs, kernel image, and \
    kernel devicetree.
"

SRC_URI = "\
    file://sharch_body.sh \
    file://install.sh \
"

IMAGE_INSTALL = "packagegroup-core-boot kernel-image kernel-devicetree ${CORE_IMAGE_EXTRA_INSTALL}"
IMAGE_LINGUAS = " "
IMAGE_FSTYPES = "tar.gz"
inherit core-image

IMAGE_ROOTFS_SIZE ?= "8192"
IMAGE_ROOTFS_EXTRA_SPACE:append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "", d)}"

addtask rootfs after do_unpack

python () {
    # Ensure we run these usually noexec tasks
    d.delVarFlag("do_fetch", "noexec")
    d.delVarFlag("do_unpack", "noexec")
}

create_onie_installer () {
	cd ${WORKDIR}

    # NOTE: need 1) config 2) install binary 3) rootfs
    mkdir -p ${WORKDIR}/installer
    cat <<EOF >${WORKDIR}/installer/yocto.conf
MACHINE=delta_tn48
ARCH=arm64
INSTALL_DISK=/dev/sda
INSTALL_FS=ext4
ROOTFS_ARCHIVE=${IMAGE_LINK_NAME}.tar.gz
INSTALL_PART=/dev/sda1
KERNEL=boot/Image
DTB=boot/delta-tn48m.dtb
EOF
    install -Dm0755 ${UNPACKDIR}/install.sh ${WORKDIR}/installer/install.sh
    install -Dm644 ${IMGDEPLOYDIR}/${IMAGE_LINK_NAME}.tar.gz ${WORKDIR}/installer/${IMAGE_LINK_NAME}.tar.gz

    # NOTE: must not use full path e.g. ${WORKDIR}/installer!
    tar -C ${WORKDIR} -cf ${WORKDIR}/sharch.tar ./installer

    # NOTE: self-extracting archive header
    cp -f ${UNPACKDIR}/sharch_body.sh \
        ${IMGDEPLOYDIR}/${IMAGE_NAME}-onie.bin
    sed -i -e "s/%%IMAGE_SHA1%%/$(cat ${WORKDIR}/sharch.tar | sha1sum | awk '{print $1}')/" \
        ${IMGDEPLOYDIR}/${IMAGE_NAME}-onie.bin

    # NOTE: installer contents are appended after the header
    cat ${WORKDIR}/sharch.tar >> ${IMGDEPLOYDIR}/${IMAGE_NAME}-onie.bin

    ln -sf ${IMAGE_NAME}-onie.bin ${IMGDEPLOYDIR}/${IMAGE_LINK_NAME}-onie.bin
}
create_onie_installer[vardepsexclude] = "DATETIME"

IMAGE_POSTPROCESS_COMMAND += " create_onie_installer;"