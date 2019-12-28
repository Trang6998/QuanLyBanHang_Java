import { SanPham } from "@/models/SanPham";
import { ThuocTinh } from "@/models/ThuocTinh";

export interface ThuocTinhSanPham {
    thuocTinhSanPhamID: number;
    sanPhamID: number;
    thuocTinhID: number;
    noiDungMoTa: string;
    sanPham?: SanPham;
    thuocTinh?: ThuocTinh;
}
