import { DatDichVu } from "@/models/DatDichVu";
import { Media } from "@/models/Media";

export interface DichVu {
    dichVuID: number;
    tenDichVu: string;
    anhDaiDien: string;
    moTaNgan: string;
    trangThai: boolean;
    baiViet: string;
    toaNha: string;
    datDichVu?: DatDichVu[];
    media?: Media[];
}
