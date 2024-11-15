prestera-objs := prestera_main.o \
	prestera_hw.o prestera_switchdev.o prestera_devlink.o prestera_fw_log.o \
	prestera_rxtx.o prestera_dsa.o prestera_router.o \
	prestera_acl.o prestera_flow.o prestera_flower.o prestera_matchall.o prestera_debugfs.o \
	prestera_ct.o prestera_ethtool.o prestera_counter.o \
	prestera_fw.o prestera_router_hw.o prestera_dcb.o prestera_qdisc.o

obj-m += prestera_pci.o
# obj-m += prestera_shm.o
obj-m := prestera.o

SRC := $(shell pwd)

all:
	$(MAKE) -C $(KERNEL_SRC) M=$(SRC)

modules_install:
	$(MAKE) -C $(KERNEL_SRC) M=$(SRC) modules_install

clean:
	rm -f *.o *~ core .depend .*.cmd *.ko *.mod.c
	rm -f Module.markers Module.symvers modules.order
	rm -rf .tmp_versions Modules.symvers
