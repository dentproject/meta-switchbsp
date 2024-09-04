LINUX_VERSION ?= "5.15.11"
LINUX_DENT_BRANCH ?= "dent-linux-5.15.y"

SRCREV_machine = "3d377b49270ba74cb612b8ba4b82779a93e8619f"

SRC_URI = " \
    git://github.com/dentproject/linux.git;name=machine;branch=${LINUX_DENT_BRANCH};protocol=https \
    file://defconfig \
    "

require linux-dentproject.inc

KERNEL_DTC_FLAGS += "-@ -H epapr"