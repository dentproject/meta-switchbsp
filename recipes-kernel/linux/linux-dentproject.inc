DESCRIPTION = "Linux Kernel from the DENT project"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

# COMPATIBLE_MACHINE ?= "^delta$"

PE = "1"
PV = "${LINUX_VERSION}+git${SRCPV}"

inherit siteinfo
require recipes-kernel/linux/linux-yocto.inc