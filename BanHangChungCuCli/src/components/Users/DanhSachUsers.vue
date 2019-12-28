<template>
    <v-flex xs12>
        <v-breadcrumbs divider="/" class="pa-0">
            <v-icon slot="divider">chevron_right</v-icon>
            <v-breadcrumbs-item>
                <v-btn flat class="ma-0" @click="$router.go(-1)" small><v-icon>arrow_back</v-icon> Quay lại</v-btn>
            </v-breadcrumbs-item>
            <v-breadcrumbs-item to="/users" exact>Users</v-breadcrumbs-item>
        </v-breadcrumbs>
        <v-card>
            <v-card-text>
                <v-layout row wrap>
                    <v-flex xs12>
                    <v-data-table :headers="tableHeader"
                                :items="dsUsers"
                                @update:pagination="getDataFromApi" :pagination.sync="searchParamsUsers"
                                :total-items="searchParamsUsers.totalItems"
                                :loading="loadingTable"
                                class="table-border table">
                        <template slot="items" slot-scope="props">
                    <td>{{ props.item.PasswordSalt }}</td>
                    <td>{{ props.item.Active ? "Yes" : "No" }}</td>
                    <td>{{ props.item.UserId }}</td>
                    <td>{{ props.item.UserName }}</td>
                    <td>{{ props.item.Email }}</td>
                            <td>{{ props.item.HoGiaDinh.HoGiaDinhId }}</td>
                            <td>{{ props.item.ToaNha.ToaNhaId }}</td>
                            <td>{{ props.item.LoaiTaiKhoan.LoaiTaiKhoanId }}</td>
                            <td>{{ props.item.Phong.PhongId }}</td>
                    <td>{{ props.item.Password }}</td>
                            <td>{{ props.item.CreatedTime === null ? "" : props.item.CreatedTime|moment('DD/MM/YYYY HH:mm:ss') }}</td>
                            <td>{{ props.item.LastLogin === null ? "" : props.item.LastLogin|moment('DD/MM/YYYY HH:mm:ss') }}</td>
                    <td>{{ props.item.Lang }}</td>
                    <td class="text-xs-center" style="width:80px;">
                        <v-btn flat icon small :to="'/users/'+props.item.UserId" class="ma-0">
                            <v-icon small>edit</v-icon>
                        </v-btn>
                        <v-btn flat color="red" icon small class="ma-0" @click="confirmDelete(props.item)">
                            <v-icon small>delete</v-icon>
                        </v-btn>
                    </td>
                            </template>
                        </v-data-table>
                    </v-flex xs12>
                </v-layout>
            </v-card-text>
        </v-card>
        <v-dialog v-model="dialogConfirmDelete" max-width="290">
                    <v-card>
                <v-card-title class="headline">Xác nhận xóa</v-card-title>
                <v-card-text class="pt-3">Bạn có chắc chắn muốn xóa?</v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn @click.native="dialogConfirmDelete=false" flat>Hủy</v-btn>
                    <v-btn color="red darken-1" @click.native="deleteUsers" flat>Xác Nhận</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-flex>
</template>
<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import UsersApi, { UsersApiSearchParams } from '@/apiResources/UsersApi';
    import { Users } from '@/models/Users';

    export default Vue.extend({
        components: {},
        data() {
            return {
                dsUsers: [] as Users[],
                tableHeader: [
                    { text: 'PasswordSalt', value: 'PasswordSalt', align: 'center', sortable: true },
                    { text: 'Active', value: 'Active', align: 'center', sortable: true },
                    { text: 'UserId', value: 'UserId', align: 'center', sortable: true },
                    { text: 'UserName', value: 'UserName', align: 'center', sortable: true },
                    { text: 'Email', value: 'Email', align: 'center', sortable: true },
                    { text: 'HoGiaDinhId', value: 'HoGiaDinh.HoGiaDinhId', align: 'center', sortable: true },
                    { text: 'ToaNhaId', value: 'ToaNha.ToaNhaId', align: 'center', sortable: true },
                    { text: 'LoaiTaiKhoanId', value: 'LoaiTaiKhoan.LoaiTaiKhoanId', align: 'center', sortable: true },
                    { text: 'PhongId', value: 'Phong.PhongId', align: 'center', sortable: true },
                    { text: 'Password', value: 'Password', align: 'center', sortable: true },
                    { text: 'CreatedTime', value: 'CreatedTime', align: 'center', sortable: true },
                    { text: 'LastLogin', value: 'LastLogin', align: 'center', sortable: true },
                    { text: 'Lang', value: 'Lang', align: 'center', sortable: true },
                    { text: 'Thao tác', value: '#', align: 'center', sortable: false },
                ],
                searchParamsUsers: { includeEntities: true, rowsPerPage: 10 } as UsersApiSearchParams,
                loadingTable: false,
                selectedUsers: {} as Users,
                dialogConfirmDelete: false,
            }
        },
        watch: {
        },
        created() {
            this.getDataFromApi(this.searchParamsUsers);
        },
        methods: {
            getDataFromApi(searchParamsUsers: UsersApiSearchParams): void {
                this.loadingTable = true;
                UsersApi.search(searchParamsUsers).then(res => {
                    this.dsUsers = res.data;
                    this.searchParamsUsers.totalItems = res.pagination.totalItems;
                    this.loadingTable = false;
                });
            },
            confirmDelete(users: Users): void {
                this.selectedUsers = users;
                this.dialogConfirmDelete = true;
            },
            deleteUsers(): void {
                UsersApi.delete(this.selectedUsers.userId).then(res => {
                    this.$snotify.success('Xóa thành công!');
                    this.getDataFromApi(this.searchParamsUsers);
                    this.dialogConfirmDelete = false;
                }).catch(res => {
                    this.$snotify.error('Xóa thất bại!');
                });
            },
        }
    });
</script>

