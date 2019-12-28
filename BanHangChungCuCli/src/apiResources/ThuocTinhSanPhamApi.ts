import { HTTP } from '@/HTTPServices'
import { BaseApi } from './BaseApi'
import { PaginatedResponse,Pagination } from './PaginatedResponse'
import { ThuocTinhSanPham } from '@/models/ThuocTinhSanPham'
export interface ThuocTinhSanPhamApiSearchParams extends Pagination {
    sanPhamID?:number;
    thuocTinhID?:number;
}
class ThuocTinhSanPhamApi extends BaseApi {
    search(searchParams: ThuocTinhSanPhamApiSearchParams): Promise<PaginatedResponse<ThuocTinhSanPham>> {

        return new Promise<PaginatedResponse<ThuocTinhSanPham>>((resolve: any, reject: any) => {
            HTTP.get('thuoctinhsanpham', {
                params: searchParams
            }).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    detail(id: number): Promise<ThuocTinhSanPham> {
        return new Promise<ThuocTinhSanPham>((resolve: any, reject: any) => {
            HTTP.get('thuoctinhsanpham/' + id).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    update(id: number, thuocTinhSanPham: ThuocTinhSanPham): Promise<ThuocTinhSanPham> {
        return new Promise<ThuocTinhSanPham>((resolve: any, reject: any) => {
            HTTP.put('thuoctinhsanpham/' + id, 
                thuocTinhSanPham
            ).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    insert(thuocTinhSanPham: ThuocTinhSanPham): Promise<ThuocTinhSanPham> {
        return new Promise<ThuocTinhSanPham>((resolve: any, reject: any) => {
            HTTP.post('thuoctinhsanpham', 
                thuocTinhSanPham
            ).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    delete(id: number): Promise<ThuocTinhSanPham> {
        return new Promise<ThuocTinhSanPham>((resolve: any, reject: any) => {
            HTTP.delete('thuoctinhsanpham/' + id)
            .then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
}
export default new ThuocTinhSanPhamApi();
