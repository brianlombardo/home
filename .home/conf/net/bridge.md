# bridge

```bash
apt install bridge-utils
```

## setup network

### interface definitions

```bash
vim  /etc/network/interfaces
```

```conf
allow-hotplug eth0
allow-hotplug eth1
allow-hotplug wlan0
auto br0
iface br0 inet dhcp
   bridge-ports eth0 eth1 wlan0
```

### netplan definitions

```bash
vim /etc/netplan/01-abc.yaml
```

```yaml
network:
    ethernets:
        eth0:
           dhcp4: false
           optional: true
        eth1:
           dhcp4: false
           optional: true
        wlan0:
           dhcp4: false
           optional: true
    bridges:
        br0:
            dhcp4: true
            interfaces:
                - eth0
                - eth1
                - wlan0
    version: 2
```

```bash
netplan generate
netplan apply
```

### broken systemd definitions

```bash
vim /etc/systemd/network/br0.netdev
```

```conf
[NetDev]
Name=br0
Kind=bridge
```

```bash
vim /etc/systemd/network/br0.network
```

```conf
[Match]
Name=br0
[Link]
RequiredForOnline=no
[Network]
Gateway=192.168.1.1
DNS=1.1.1.1
[Address]
Address=192.168.1.1/24
Broadcast=192.168.1.255
```

```bash
vim /etc/systemd/network/br0-members.network
```

```conf
[Match]
Name=eth0
[Network]
Bridge=br0
```

```bash
systemctl enable systemd-networkd.service
```

## offline host

only allow connections on bridged interfaces

```bash
apt install ufw
ufw default deny incoming
ufw default deny outgoing
ufw default deny forward
ufw enable
```
