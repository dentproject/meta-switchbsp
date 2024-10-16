#!/bin/sh

set -e

cd $(dirname $0)

# Load configuration variables
. /etc/machine.conf
. yocto.conf || exit 1

echo "------------------------------------"
echo "        ONIE Yocto installer"
echo " Architecture: $onie_arch"
echo " Machine:      $onie_machine"
echo
echo "------------------------------------"

echo "Partitioning disk..."
parted --script -a optimal $INSTALL_DISK \
	mklabel msdos \
	mkpart primary $INSTALL_FS 0% 100%

echo "Formatting disk..."
yes | mkfs.ext4 $INSTALL_PART

mkdir hdd-disk
mount -t ext4 $INSTALL_PART hdd-disk

echo "Extracting rootfs..."
tar -xpf $ROOTFS_ARCHIVE -C hdd-disk --numeric-owner

sync
umount hdd-disk

echo "Setting boot environment..."

fw_setenv -f yocto_bootargs "root=$INSTALL_PART init=/sbin/init rw"

[ -z "$(fw_printenv kernel_addr_r 2>/dev/null)" ] && \
	fw_setenv -f kernel_addr_r $KERNEL_ADDR_R

[ -z "$(fw_printenv fdt_addr_r 2>/dev/null)" ] && \
	fw_setenv -f fdt_addr_r $FDT_ADDR_R

fw_setenv -f boot_yocto "scsi scan; ext4load scsi 0:1 \${kernel_addr_r} $KERNEL; ext4load scsi 0:1 \${fdt_addr_r} $DTB; setenv bootargs \${yocto_bootargs}; booti \${kernel_addr_r} - \${fdt_addr_r}"
fw_setenv -f nos_bootcmd 'run boot_yocto'

sync

if [ -x /bin/onie-nos-mode ] ; then
    /bin/onie-nos-mode -s
fi

echo "Done, rebooting..."
