import { HTTP } from '@/HTTPServices'
import { BaseApi } from './BaseApi'
import { PaginatedResponse,Pagination } from './PaginatedResponse'
import { DonDatHang } from '@/models/DonDatHang'
export interface DonDatHangApiSearchParams extends Pagination {
    taiKhoanDatHangID?:number;
    taiKhoanNhanVienID?: number;
    tinhTrang?: number;
    tuNgay?: Date;
    denNgay?: Date;
    toaNhaID?: number;
    soHoaDon: string;
    sanPhamID?: number;
    nhaCungCapID?: number;
}
export interface TongHopDonApiSearchParams extends Pagination {
    tinhTrang?: number;
    tuNgay?: Date;
    denNgay?: Date;
}
export interface GioHangApiSearchParams extends Pagination {
    taiKhoanKhachHangID?: number;
}
class DonDatHangApi extends BaseApi {
    search(searchParams: DonDatHangApiSearchParams): Promise<PaginatedResponse<DonDatHang>> {

        return new Promise<PaginatedResponse<DonDatHang>>((resolve: any, reject: any) => {
            HTTP.get('dondathang', {
                params: searchParams
            }).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    tonghopdon(searchParams: TongHopDonApiSearchParams): Promise<PaginatedResponse<any>> {

        return new Promise<PaginatedResponse<any>>((resolve: any, reject: any) => {
            HTTP.get('dondathang/tongHopDon', {
                params: searchParams
            }).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    getgiohang(searchParams: GioHangApiSearchParams): Promise<PaginatedResponse<any>> {

        return new Promise<PaginatedResponse<any>>((resolve: any, reject: any) => {
            HTTP.get('dondathang/gioHang', {
                params: searchParams
            }).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    detail(id: number): Promise<DonDatHang> {
        return new Promise<DonDatHang>((resolve: any, reject: any) => {
            HTTP.get('dondathang/' + id).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    update(id: number, donDatHang: DonDatHang): Promise<DonDatHang> {
        return new Promise<DonDatHang>((resolve: any, reject: any) => {
            HTTP.put('dondathang/' + id, 
                donDatHang
            ).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    insert(sanPhamID?: number, soLuong?: number, giaXuat?: number, donDatHang?: DonDatHang): Promise<DonDatHang> {
        return new Promise<DonDatHang>((resolve: any, reject: any) => {
            HTTP.post('dondathang?sanPhamID=' + sanPhamID + '&soLuong=' + soLuong + '&giaXuat=' + giaXuat, 
                donDatHang
            ).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    delete(id: number): Promise<DonDatHang> {
        return new Promise<DonDatHang>((resolve: any, reject: any) => {
            HTTP.delete('dondathang/' + id)
            .then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
}
export default new DonDatHangApi();
