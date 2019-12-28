import { LoaiSanPham } from "@/models/LoaiSanPham";
import { SanPham } from "@/models/SanPham";

export interface LoaiSanPham {
    loaiSanPhamID: number;
    loaiSanPhamPID: number;
    tenLoai: string;
    moTa: string;
    anhDaiDien: string;
    loaiSanPham2?: LoaiSanPham; // cha
    loaiSanPham1?: LoaiSanPham[]; // con
    sanPham?: SanPham[];
}
