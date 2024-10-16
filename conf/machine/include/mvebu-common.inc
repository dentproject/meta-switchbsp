require conf/machine/include/arm/arch-armv8a.inc
SOC_FAMILY:prepend = "mvebu:"
require conf/machine/include/soc-family.inc

KERNEL_DEVICETREE = " \
    marvell/accton-as4224.dtb \
    marvell/accton-as5114.dtb \
    marvell/accton-as4564-26p.dtb \
    marvell/delta-tn48m.dtb \
    marvell/delta-tn4810m.dtb \
    marvell/delta-tn48m-dn.dtb \
    marvell/delta-tn4810m-dn.dtb \
    marvell/delta-tn48m2-swdev.dtb \
    marvell/delta-tn48m.dtb \
"

SERIAL_CONSOLE ?= "115200 ttyS0"