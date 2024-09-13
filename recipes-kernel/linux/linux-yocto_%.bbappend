COMPATIBLE_MACHINE = "(.*)"

FILESEXTRAPATHS:prepend := "${THISDIR}/linux-yocto:"

SRC_URI:append = " \
    file://switch-kmeta;type=kmeta;name=switch-kmeta;destsuffix=switch-kmeta \
"