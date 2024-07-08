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
>>>>>>> 2417fd5 (initial commit)
