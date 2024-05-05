import socket
import struct
import textwrap

def eth_frame(data):
    dest_mac, src_mac, protocol = struct.unpack('! 6S 6S H', data[:14])
    return get_addr(dest_mac), get_addr(src_mac), socket.htons(protocol), data[14:]