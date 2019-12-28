import { SanPhamNhaCungCap } from "@/models/SanPhamNhaCungCap";

export interface NhaCungCap {
    nhaCungCapID: number;
    tenNhaCungCap: string;
    diaChi: string;
    soDienThoai: string;
    email: string;
    ghiChu: string;
    maNhaCungCap: string;
    sanPhamNhaCungCap?: SanPhamNhaCungCap[];
}
