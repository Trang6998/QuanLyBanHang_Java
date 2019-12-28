import { ChiTietDonDatHang } from "@/models/ChiTietDonDatHang";
import { Media } from "@/models/Media";
import { ThuocTinhSanPham } from "@/models/ThuocTinhSanPham";
import { SanPhamNhaCungCap } from "@/models/SanPhamNhaCungCap";
import { DonViTinh } from "@/models/DonViTinh";
import { LoaiSanPham } from "@/models/LoaiSanPham";

export interface SanPham {
    sanPhamID: number;
    loaiSanPhamID: number;
    donViTinh: string;
    kyHieuSanPham: string;
    tenSanPham: string;
    anhSanPham: string;
    moTaNgan: string;
    moTa: string;
    nhaSanXuat: string;
    anHienSanPham: boolean;
    anHienNhaSanXuat: boolean;
    giaBan: number;
    toaNhaID: number;
    thuTu: number;
    ngayDang: Date;
    loaiSanPham?: LoaiSanPham;
    chiTietDonDatHang?: ChiTietDonDatHang[];
    media?: Media[];
    thuocTinhSanPham?: ThuocTinhSanPham[];
    sanPhamNhaCungCap?: SanPhamNhaCungCap[];
}
