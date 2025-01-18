PREFERRED_PROVIDER_virtual/kernel ?= "linux-yocto"

KMACHINE = "arm64-switch"
KERNEL_IMAGETYPE ?= "Image"

require conf/machine/include/arm/arch-armv8a.inc

MACHINE_FEATURES += "pci serial"