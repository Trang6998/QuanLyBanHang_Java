<template>
    <v-flex xs12>
        <v-card>
            <v-card-text>
                <v-layout row wrap>
                    <v-flex xs12><h3>Hủy hàng/Thanh lý</h3></v-flex>
                    <v-flex xs12 md3>
                        <v-text-field label="Tìm kiếm" placeholder="Tìm kiếm theo số hóa đơn..." hide-details v-model="searchParamsDonDatHang.soHoaDon" @input="getDataFromApi(searchParamsDonDatHang)" ma-0 pa-0></v-text-field>
                    </v-flex>
                    <v-flex xs12 md3>
                        <v-datetimepicker label="Từ ngày" hide-details v-model="searchParamsDonDatHang.tuNgay"
                                          :max="searchParamsDonDatHang.tuNgay"
                                          @input="getDataFromApi(searchParamsDonDatHang)"></v-datetimepicker>
                    </v-flex>
                    <v-flex xs12 md3>
                        <v-datetimepicker label="Đến ngày" hide-details v-model="searchParamsDonDatHang.denNgay"
                                          :min="searchParamsDonDatHang.denNgay" @input="getDataFromApi(searchParamsDonDatHang)"></v-datetimepicker>
                    </v-flex>
                    <v-flex xs12 sm3>
                        <v-select :items="dsToaNha"
                                  placeholder="Chọn chung cư"
                                  label="Chọn chung cư"
                                  v-model="searchParamsDonDatHang.toaNhaID"
                                  @change="getDataFromApi(searchParamsDonDatHang)"
                                  item-value="ToaNhaId"
                                  item-text="TenToaNha"
                                  hide-details
                                  autocomplete></v-select>
                    </v-flex>
                    <v-flex xs12>
                        <v-layout nowrap>
                            <v-spacer></v-spacer>
                            <v-btn small @click="showCapNhatHoaDon(false,{})" style="margin-top: auto" color="primary">Thêm mới</v-btn>
                        </v-layout>
                    </v-flex>
                    <v-flex xs12>
                        <v-data-table :headers="tableHeader"
                                      :items="dsDonDatHang"
                                      @update:pagination="getDataFromApi" :pagination.sync="searchParamsDonDatHang"
                                      :total-items="searchParamsDonDatHang.totalItems"
                                      :loading="loadingTable"
                                      class="table-border table">
                            <template slot="items" slot-scope="props">
                                <td>{{ props.index + 1 }}</td>
                                <td>{{ props.item.soHieuDon }}</td>
                                <td>{{ props.item.ngayDat === null ? "" : props.item.ngayDat|moment('DD/MM/YYYY HH:mm') }}</td>
                                <td>{{ props.item.tenNhanVien}}</td>
                                <td>{{ props.item.tongTien }}</td>
                                <td>{{ props.item.ghiChu }}</td>
                                <td class="text-xs-center text-nowrap" style="width:100px;">
                                    <v-btn icon small class="ma-0" @click="showCapNhatHoaDon(true,props.item)">
                                        <v-icon small color="teal">edit</v-icon>
                                    </v-btn>
                                    <v-btn icon small class="ma-0" @click="confirmDelete(props.item)">
                                        <v-icon small color="red">delete</v-icon>
                                    </v-btn>
                                </td>
                            </template>
                            <template slot="no-data">
                                <p class="text-xs-center mb-0">Không có dữ liệu</p>
                            </template>
                        </v-data-table>
                    </v-flex>

                </v-layout>
            </v-card-text>
        </v-card>
        <cap-nhat-hoa-don ref="capNhatHoaDon" @getData="getDataFromApi(searchParamsDonDatHang)" ></cap-nhat-hoa-don>
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
    import { HTTP } from '@/http-servicesNew';
    import CapNhatHoaDon from './CapNhatHoaDon.vue'

    export default Vue.extend({
        components: { CapNhatHoaDon },
        data() {
            return {
                dsDonDatHang: [] as DonDatHang[],
                tableHeader: [
                    { text: '#', value: '#', align: 'center', sortable: true },
                    { text: 'Số đơn', value: 'soHieuDon', align: 'center', sortable: true },
                    { text: 'Ngày thanh lý', value: 'ngayDat', align: 'center', sortable: true },
                    { text: 'Người tạo', value: '#', align: 'center', sortable: true },
                    { text: 'Tổng tiền (VNĐ)', value: 'tongTien', align: 'center', sortable: true },
                    { text: 'Ghi chú', value: 'trangThaiDon', align: 'center', sortable: true },
                    { text: 'Thao tác', value: '#', align: 'center', sortable: true },
                ],
                searchParamsDonDatHang: { includeEntities: true, rowsPerPage: 10, tinhTrang: 5 } as DonDatHangApiSearchParams,
                loadingTable: false,
                selectedDonDatHang: {} as DonDatHang,
                dialogConfirmDelete: false,
                dsToaNha: [] as any,
            }
        },
        watch: {
        },
        created() {
            this.getDataFromApi(this.searchParamsDonDatHang);
            this.getDanhSachToaNha();
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
            showCapNhatHoaDon(isUpdate: boolean, hoaDon: DonDatHang) {
                (this.$refs.capNhatHoaDon as any).show(isUpdate, hoaDon);
            },
            confirmDelete(datDichVu: DonDatHang): void {
                this.selectedDonDatHang = datDichVu;
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
            getDanhSachToaNha() {
                HTTP.get('odata/ToaNha').then(res => {
                    this.dsToaNha.push({
                        ToaNhaId: null as any,
                        TenToaNha: 'Tất cả'
                    } as any);
                    this.dsToaNha.push(...res.data.value);
                    this.searchParamsDonDatHang.toaNhaID = null as any;
                })
            },
        }
    });
</script>

