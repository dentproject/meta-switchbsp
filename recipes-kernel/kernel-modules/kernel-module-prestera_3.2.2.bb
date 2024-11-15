SUMMARY = "Marvell's out-of-tree kernel switchdev module for Prestera"
DESCRIPTION = "${SUMMARY}"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

SRCBRANCH = "main"
SRCREV = "eef91b332257eb2fe2d8e303085a1109863c46fe"

inherit module

SRC_URI = " \
    git://github.com/Marvell-switching/switchdev-prestera.git;protocol=https;branch=${SRCBRANCH} \
    file://Makefile;subdir=git/prestera \
"

S = "${WORKDIR}/git/prestera"

RPROVIDES_${PN} += "kernel-module-prestera"