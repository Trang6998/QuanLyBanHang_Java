import { DichVu } from "@/models/DichVu";
import { SanPham } from "@/models/SanPham";

export interface Media {
    mediaID: number;
    dichVuID: number;
    sanPhamID: number;
    laAnh: number;
    duongLink: string;
    moTa: string;
    dichVu?: DichVu;
    sanPham?: SanPham;
}
