import { NhaCungCap } from "@/models/NhaCungCap";
import { SanPham } from "@/models/SanPham";
import { Users } from "@/models/Users";

export interface SanPhamNhaCungCap {
    sanPhamNhaCungCapID: number;
    nhaCungCapID: number;
    sanPhamID: number;
    nhanVienID: number;
    ngayNhap: Date;
    soLuong: number;
    donGiaNhap: number;
    ghiChu: string;
    soThuTuBan: number;
    nhaCungCap?: NhaCungCap;
    sanPham?: SanPham;
    users?: Users;
}
