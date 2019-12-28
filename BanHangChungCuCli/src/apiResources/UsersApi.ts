import { HTTP } from '@/HTTPServices'
import { BaseApi } from './BaseApi'
import { PaginatedResponse,Pagination } from './PaginatedResponse'
import { Users } from '@/models/Users'
export interface UsersApiSearchParams extends Pagination {
    hoGiaDinhId?:number;
    toaNhaId?:number;
    loaiTaiKhoanId?:number;
    phongId?:number;
}
class UsersApi extends BaseApi {
    search(searchParams: UsersApiSearchParams): Promise<PaginatedResponse<Users>> {

        return new Promise<PaginatedResponse<Users>>((resolve: any, reject: any) => {
            HTTP.get('users', {
                params: searchParams
            }).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    detail(id: number): Promise<Users> {
        return new Promise<Users>((resolve: any, reject: any) => {
            HTTP.get('users/' + id).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    update(id: number, users: Users): Promise<Users> {
        return new Promise<Users>((resolve: any, reject: any) => {
            HTTP.put('users/' + id, 
                users
            ).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    insert(users: Users): Promise<Users> {
        return new Promise<Users>((resolve: any, reject: any) => {
            HTTP.post('users', 
                users
            ).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    delete(id: number): Promise<Users> {
        return new Promise<Users>((resolve: any, reject: any) => {
            HTTP.delete('users/' + id)
            .then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
}
export default new UsersApi();
