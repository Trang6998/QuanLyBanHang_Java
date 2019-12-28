import { HTTP } from '@/HTTPServices'
import { BaseApi } from './BaseApi'
import { PaginatedResponse,Pagination } from './PaginatedResponse'
import { ChiTietDonDatHang } from '@/models/ChiTietDonDatHang'
export interface ChiTietDonDatHangApiSearchParams extends Pagination {
    donDatHangID?:number;
    sanPhamID?: number;
    nhaCungCapID?: number;
    toaNhaID?: number;
    tuNgay?: Date;
    denNgay?: Date;
    tinhTrang?: number;
}
class ChiTietDonDatHangApi extends BaseApi {
    search(searchParams: ChiTietDonDatHangApiSearchParams): Promise<PaginatedResponse<ChiTietDonDatHang>> {

        return new Promise<PaginatedResponse<ChiTietDonDatHang>>((resolve: any, reject: any) => {
            HTTP.get('chitietdondathang', {
                params: searchParams
            }).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    detail(id: number): Promise<ChiTietDonDatHang> {
        return new Promise<ChiTietDonDatHang>((resolve: any, reject: any) => {
            HTTP.get('chitietdondathang/' + id).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    update(id: number, chiTietDonDatHang: ChiTietDonDatHang): Promise<ChiTietDonDatHang> {
        return new Promise<ChiTietDonDatHang>((resolve: any, reject: any) => {
            HTTP.put('chitietdondathang/' + id, 
                chiTietDonDatHang
            ).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    insert(chiTietDonDatHang: ChiTietDonDatHang): Promise<ChiTietDonDatHang> {
        return new Promise<ChiTietDonDatHang>((resolve: any, reject: any) => {
            HTTP.post('chitietdondathang', 
                chiTietDonDatHang
            ).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    delete(id: number): Promise<ChiTietDonDatHang> {
        return new Promise<ChiTietDonDatHang>((resolve: any, reject: any) => {
            HTTP.delete('chitietdondathang/' + id)
            .then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
}
export default new ChiTietDonDatHangApi();
