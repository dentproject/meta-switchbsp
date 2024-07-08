meta-switchbsp
==============

OpenEmbedded / Yocto BSP layer for disaggregated network switch platforms.

## Description

The current target release for this layer is v5.1 (`styhead`).

This layer depends on the following layers:

* `openembedded-core` or Yocto's `poky`
- https://git.openembedded.org/openembedded-core
- https://git.yoctoproject.org/poky

* `meta-oe` (part of `meta-openembedded`)
- https://github.com/openembedded/meta-openembedded.git

The following `MACHINE` targets are available in this layer:

* `arm64-all`
* `delta-tn48m`

## Quick Start

You can build an image using the following steps:

1. Clone build system (OE-Core or Poky), this layer, and dependencies. For example:
```
~ $ git clone https://github.com/yoctoproject/poky.git

~ $ git clone https://github.com/openembedded/meta-openembedded.git poky/meta-openembedded

~ $ git clone <this-repo-url> poky/meta-switchbsp
```

2. Source `oe-init-build-env` in the build system

3. Add this layer using `bitbake-layers add-layer` and all dependencies

```
~/poky/build $ bitbake-layers add-layer ../meta-openembedded/meta-oe
~/poky/build $ bitbake-layers add-layer ../meta-switchbsp
```

4. Set `MACHINE` in `conf/local.conf` to one of the supported targets

```
~/poky/build $ sed -i '/^MACHINE/c \MACHINE ?= "delta-tn48m"' conf/local.conf
```

5. Build a target e.g. `bitbake core-image-minimal`

```
~/poky/build $ bitbake core-image-minimal-initramfs
```

## Testing

To boot the kernel with initramfs the following procedure can be used:

 1. Enter U-Boot shell and configure TFTP

```
(...)
U-Boot 2018.03-devel-1.2.0 (Jul 17 2020 - 13:58:51 +0800) TN48M/TN48M-P/TN4810M/TN48M2 V05

Model: Marvell Armada 7040 TN48M/TN48M-P/TN4810M/TN48M2
SoC: Armada7040-B0; AP806-B0; CP115-A0
(...)
Type 123<ENTER> to STOP autoboot
Marvell>> setenv serverip 192.168.101.222 && setenv ipaddr 192.168.101.157 && ping 192.168.101.222
mvpp2-2 Waiting for PHY auto negotiation to complete... done
Using mvpp2-2 device
host 192.168.101.222 is alive
```

 2. Load the kernel and DTB file to device memory

```
Marvell>> tftpboot ${kernel_addr_r} Image-initramfs-delta-tn48m.bin && tftpboot ${fdt_addr_r} delta-tn48m.dtb
Using mvpp2-2 device
TFTP from server 192.168.101.222; our IP address is 192.168.101.157
Filename 'Image-initramfs-delta-tn48m.bin'.
Load address: 0x7000000
Loading: #################################################################
         (...)
         ###################################
         5.3 MiB/s
done
Bytes transferred = 101478912 (60c7200 hex)
Using mvpp2-2 device
TFTP from server 192.168.101.222; our IP address is 192.168.101.157
Filename 'delta-tn48m.dtb'.
Load address: 0x6f00000
Loading: ###
         4.4 MiB/s
done
Bytes transferred = 32034 (7d22 hex)
```

 3. Set `bootargs` environment variable and boot the kernel image

```
Marvell>> setenv bootargs "$console $root $extra_params $cpuidle debugshell=3 init=/bin/busybox" && booti ${kernel_addr_r} - ${fdt_addr_r}
## Flattened Device Tree blob at 06f00000
   Booting using the fdt blob at 0x6f00000
   Using Device Tree in place at 0000000006f00000, end 0000000006f0ad21

Starting kernel ...

[    0.000000] Booting Linux on physical CPU 0x0000000000 [0x410fd081]
[    0.000000] Linux version 5.15.11-yocto-standard (oe-user@oe-host) (aarch64-poky-linux-musl-gcc (GCC) 14.1.0, GNU ld (GNU Binutils) 2.42.0.20240620) #1 SMP PREEMPT Wed Sep 13 16:25:22 UTC 2023
[    0.000000] Machine model: delta,tn48m
[    0.000000] earlycon: uart8250 at MMIO32 0x00000000f0512000 (options '')
[    0.000000] printk: bootconsole [uart8250] enabled
(...)
[    2.909442] Run /init as init process
[    2.928695] udevd[193]: starting version 3.2.14
[    2.938436] udevd[194]: starting eudev-3.2.14
Waiting for removable media...
  3  2  1  0...
Mounted filesystems
Available block devices
major minor  #blocks  name

   1        0     262144 ram0
(...)
  31        0       4032 mtdblock0
  31        1         64 mtdblock1
  31        2      12288 mtdblock2
   8        0   26871264 sda
   8        1   26869760 sda1
Poky Tiny Reference Distribution:

sh: can't access tty; job control turned off
~ # strings /proc/device-tree/switch-cpu/compatible
marvell,prestera-switch-rxtx-sdma
```
