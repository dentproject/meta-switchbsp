PREFERRED_PROVIDER_virtual/kernel ?= "linux-yocto"

KMACHINE = "x86_64-switch"
KERNEL_IMAGETYPE ?= "bzImage"

DEFAULTTUNE ?= "corei7-64"
require conf/machine/include/x86/tune-corei7.inc
require conf/machine/include/x86/x86-base.inc

MACHINE_FEATURES += "pcbios efi"