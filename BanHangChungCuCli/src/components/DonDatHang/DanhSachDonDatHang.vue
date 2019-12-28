<template>
    <v-flex xs12>
        <v-breadcrumbs divider="/" class="pa-0">
            <v-icon slot="divider">chevron_right</v-icon>
            <v-breadcrumbs-item>
                <v-btn flat class="ma-0" @click="$router.go(-1)" small><v-icon>arrow_back</v-icon> Quay lại</v-btn>
            </v-breadcrumbs-item>
            <v-breadcrumbs-item to="/dondathang" exact>DonDatHang</v-breadcrumbs-item>
        </v-breadcrumbs>
        <v-card>
            <v-card-text>
                <v-layout row wrap>
                    <v-flex xs12>
                    <v-data-table :headers="tableHeader"
                                :items="dsDonDatHang"
                                @update:pagination="getDataFromApi" :pagination.sync="searchParamsDonDatHang"
                                :total-items="searchParamsDonDatHang.totalItems"
                                :loading="loadingTable"
                                class="table-border table">
                        <template slot="items" slot-scope="props">
                    <td>{{ props.item.DonDatHangID }}</td>
                            <td>{{ props.item.Users.UserId }}</td>
                            <td>{{ props.item.Users.UserId }}</td>
                    <td>{{ props.item.SoHieuDon }}</td>
                            <td>{{ props.item.NgayDat === null ? "" : props.item.NgayDat|moment('DD/MM/YYYY HH:mm:ss') }}</td>
                            <td>{{ props.item.HenLayTu === null ? "" : props.item.HenLayTu|moment('DD/MM/YYYY HH:mm:ss') }}</td>
                            <td>{{ props.item.HenLayDen === null ? "" : props.item.HenLayDen|moment('DD/MM/YYYY HH:mm:ss') }}</td>
                            <td>{{ props.item.HenTraTu === null ? "" : props.item.HenTraTu|moment('DD/MM/YYYY HH:mm:ss') }}</td>
                            <td>{{ props.item.HenTraDen === null ? "" : props.item.HenTraDen|moment('DD/MM/YYYY HH:mm:ss') }}</td>
                    <td>{{ props.item.LaTre ? "Yes" : "No" }}</td>
                    <td>{{ props.item.LyDoTraTre }}</td>
                            <td>{{ props.item.NgayGioTraThucTe === null ? "" : props.item.NgayGioTraThucTe|moment('DD/MM/YYYY HH:mm:ss') }}</td>
                    <td>{{ props.item.GhiChu }}</td>
                    <td>{{ props.item.TinhTrang }}</td>
                    <td class="text-xs-center" style="width:80px;">
                        <v-btn flat icon small :to="'/dondathang/'+props.item.DonDatHangID" class="ma-0">
                            <v-icon small>edit</v-icon>
                        </v-btn>
                        <v-btn flat color="red" icon small class="ma-0" @click="confirmDelete(props.item)">
                            <v-icon small>delete</v-icon>
                        </v-btn>
                    </td>
                            </template>
                        </v-data-table>
                    </v-flex>
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
                    <v-btn color="red darken-1" @click.native="deleteDonDatHang" flat>Xác Nhận</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-flex>
</template>
<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import DonDatHangApi, { DonDatHangApiSearchParams } from '@/apiResources/DonDatHangApi';
    import { DonDatHang } from '@/models/DonDatHang';

    export default Vue.extend({
        components: {},
        data() {
            return {
                dsDonDatHang: [] as DonDatHang[],
                tableHeader: [
                    { text: 'DonDatHangID', value: 'DonDatHangID', align: 'center', sortable: true },
                    { text: 'TaiKhoanDatHangID', value: 'Users.UserId', align: 'center', sortable: true },
                    { text: 'TaiKhoanNhanVienID', value: 'Users.UserId', align: 'center', sortable: true },
                    { text: 'SoHieuDon', value: 'SoHieuDon', align: 'center', sortable: true },
                    { text: 'NgayDat', value: 'NgayDat', align: 'center', sortable: true },
                    { text: 'HenLayTu', value: 'HenLayTu', align: 'center', sortable: true },
                    { text: 'HenLayDen', value: 'HenLayDen', align: 'center', sortable: true },
                    { text: 'HenTraTu', value: 'HenTraTu', align: 'center', sortable: true },
                    { text: 'HenTraDen', value: 'HenTraDen', align: 'center', sortable: true },
                    { text: 'LaTre', value: 'LaTre', align: 'center', sortable: true },
                    { text: 'LyDoTraTre', value: 'LyDoTraTre', align: 'center', sortable: true },
                    { text: 'NgayGioTraThucTe', value: 'NgayGioTraThucTe', align: 'center', sortable: true },
                    { text: 'GhiChu', value: 'GhiChu', align: 'center', sortable: true },
                    { text: 'TinhTrang', value: 'TinhTrang', align: 'center', sortable: true },
                    { text: 'Thao tác', value: '#', align: 'center', sortable: false },
                ],
                searchParamsDonDatHang: { includeEntities: true, rowsPerPage: 10 } as DonDatHangApiSearchParams,
                loadingTable: false,
                selectedDonDatHang: {} as DonDatHang,
                dialogConfirmDelete: false,
            }
        },
        watch: {
        },
        created() {
            this.getDataFromApi(this.searchParamsDonDatHang);
        },
        methods: {
            getDataFromApi(searchParamsDonDatHang: DonDatHangApiSearchParams): void {
                this.loadingTable = true;
                DonDatHangApi.search(searchParamsDonDatHang).then(res => {
                    this.dsDonDatHang = res.data;
                    this.searchParamsDonDatHang.totalItems = res.pagination.totalItems;
                    this.loadingTable = false;
                });
            },
            confirmDelete(donDatHang: DonDatHang): void {
                this.selectedDonDatHang = donDatHang;
                this.dialogConfirmDelete = true;
            },
            deleteDonDatHang(): void {
                DonDatHangApi.delete(this.selectedDonDatHang.donDatHangID).then(res => {
                    this.$snotify.success('Xóa thành công!');
                    this.getDataFromApi(this.searchParamsDonDatHang);
                    this.dialogConfirmDelete = false;
                }).catch(res => {
                    this.$snotify.error('Xóa thất bại!');
                });
            },
        }
    });
</script>

