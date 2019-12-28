-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th12 28, 2019 lúc 06:10 PM
-- Phiên bản máy phục vụ: 10.1.38-MariaDB
-- Phiên bản PHP: 7.1.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `banhang`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitietdonhang`
--

CREATE TABLE `chitietdonhang` (
  `chitietdathangid` int(11) NOT NULL,
  `đonathangid` int(11) DEFAULT NULL,
  `sanphamid` int(11) DEFAULT NULL,
  `soluong` int(11) DEFAULT NULL,
  `giaxuat` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `datdichvu`
--

CREATE TABLE `datdichvu` (
  `datdichvuid` int(11) NOT NULL,
  `userid` int(11) DEFAULT NULL,
  `dichvuid` int(11) DEFAULT NULL,
  `ngaydat` datetime DEFAULT NULL,
  `yeucau` text COLLATE utf8_unicode_ci,
  `ghichu` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `dichvu`
--

CREATE TABLE `dichvu` (
  `dichvuid` int(11) NOT NULL,
  `tendichvu` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `anhdaidien` text COLLATE utf8_unicode_ci,
  `motangan` text COLLATE utf8_unicode_ci,
  `trangthai` bit(2) DEFAULT NULL,
  `baiviet` text COLLATE utf8_unicode_ci,
  `toanha` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `dondathang`
--

CREATE TABLE `dondathang` (
  `dondathangid` int(11) NOT NULL,
  `taikhoandathangid` int(11) DEFAULT NULL,
  `taikhoannhanvienid` int(11) DEFAULT NULL,
  `sohieudon` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ngaydat` datetime DEFAULT NULL,
  `henlaytu` datetime DEFAULT NULL,
  `denlaytu` datetime DEFAULT NULL,
  `hentratu` datetime DEFAULT NULL,
  `hentraden` datetime DEFAULT NULL,
  `latre` bit(2) DEFAULT NULL,
  `lydolatre` text COLLATE utf8_unicode_ci,
  `ngaygiothuctratre` datetime DEFAULT NULL,
  `ghichu` text COLLATE utf8_unicode_ci,
  `tinhtrang` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `donvitinh`
--

CREATE TABLE `donvitinh` (
  `donvitinhid` int(11) NOT NULL,
  `tendonvi` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ghichu` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `loaisanpham`
--

CREATE TABLE `loaisanpham` (
  `loaisanphamid` int(11) NOT NULL,
  `loaisanphampid` int(11) DEFAULT NULL,
  `tenloai` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mota` text COLLATE utf8_unicode_ci,
  `anhdaidien` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `loaisanpham`
--

INSERT INTO `loaisanpham` (`loaisanphamid`, `loaisanphampid`, `tenloai`, `mota`, `anhdaidien`) VALUES
(1, NULL, 'nước ngọt', NULL, NULL),
(2, NULL, 'kẹo', NULL, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `media`
--

CREATE TABLE `media` (
  `mediaid` int(11) NOT NULL,
  `dichvuid` int(11) DEFAULT NULL,
  `sanphamid` int(11) DEFAULT NULL,
  `laanh` int(11) DEFAULT NULL,
  `duonglink` text COLLATE utf8_unicode_ci,
  `mota` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhacungcap`
--

CREATE TABLE `nhacungcap` (
  `nhacungcapid` int(11) NOT NULL,
  `tennhacungcap` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `diachi` text COLLATE utf8_unicode_ci,
  `sodienthoai` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ghichu` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sanpam`
--

CREATE TABLE `sanpam` (
  `sanphamid` int(11) NOT NULL,
  `loaisanphamid` int(11) DEFAULT NULL,
  `donvitinhid` int(11) DEFAULT NULL,
  `kyhieusanpham` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tensanpham` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `anhsanpham` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `motangan` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mota` text COLLATE utf8_unicode_ci,
  `nhasanxuat` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `anhiensanpham` bit(2) DEFAULT NULL,
  `anhiennhasanxuat` bit(2) DEFAULT NULL,
  `giaban` float DEFAULT NULL,
  `toanha` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `thutu` int(11) DEFAULT NULL,
  `ngaydang` date DEFAULT NULL,
  `lasanphammoi` bit(2) DEFAULT NULL,
  `nhacungcapid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `sanpam`
--

INSERT INTO `sanpam` (`sanphamid`, `loaisanphamid`, `donvitinhid`, `kyhieusanpham`, `tensanpham`, `anhsanpham`, `motangan`, `mota`, `nhasanxuat`, `anhiensanpham`, `anhiennhasanxuat`, `giaban`, `toanha`, `thutu`, `ngaydang`, `lasanphammoi`, `nhacungcapid`) VALUES
(1, 1, NULL, NULL, 'coca cola', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(2, 1, NULL, NULL, 'pepsi', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thuoctinh`
--

CREATE TABLE `thuoctinh` (
  `thuoctinhid` int(11) NOT NULL,
  `tenthuoctinh` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ghichu` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thuoctinhsanpham`
--

CREATE TABLE `thuoctinhsanpham` (
  `thuoctinhsanphamid` int(11) NOT NULL,
  `sanphamid` int(11) DEFAULT NULL,
  `thuoctinhid` int(11) DEFAULT NULL,
  `noidungmota` text COLLATE utf8_unicode_ci,
  `ghichu` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `userid` int(11) NOT NULL,
  `ngaylap` datetime DEFAULT NULL,
  `hoten` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ghichu` text COLLATE utf8_unicode_ci,
  `diachi` text COLLATE utf8_unicode_ci,
  `loaiuser` int(11) DEFAULT NULL,
  `password` text COLLATE utf8_unicode_ci,
  `taikhoan` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sodienthoai` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  ADD PRIMARY KEY (`chitietdathangid`),
  ADD KEY `DonDatHangID` (`đonathangid`),
  ADD KEY `SanPhamID` (`sanphamid`);

--
-- Chỉ mục cho bảng `datdichvu`
--
ALTER TABLE `datdichvu`
  ADD PRIMARY KEY (`datdichvuid`),
  ADD KEY `UserID` (`userid`),
  ADD KEY `DichVuID` (`dichvuid`);

--
-- Chỉ mục cho bảng `dichvu`
--
ALTER TABLE `dichvu`
  ADD PRIMARY KEY (`dichvuid`);

--
-- Chỉ mục cho bảng `dondathang`
--
ALTER TABLE `dondathang`
  ADD PRIMARY KEY (`dondathangid`),
  ADD KEY `TaiKhoanDatHangID` (`taikhoandathangid`),
  ADD KEY `TaiKhoanNhanVienID` (`taikhoannhanvienid`);

--
-- Chỉ mục cho bảng `donvitinh`
--
ALTER TABLE `donvitinh`
  ADD PRIMARY KEY (`donvitinhid`);

--
-- Chỉ mục cho bảng `loaisanpham`
--
ALTER TABLE `loaisanpham`
  ADD PRIMARY KEY (`loaisanphamid`),
  ADD KEY `LoaiSanPhamPID` (`loaisanphampid`);

--
-- Chỉ mục cho bảng `media`
--
ALTER TABLE `media`
  ADD PRIMARY KEY (`mediaid`),
  ADD KEY `DichVuID` (`dichvuid`),
  ADD KEY `SanPhamID` (`sanphamid`);

--
-- Chỉ mục cho bảng `nhacungcap`
--
ALTER TABLE `nhacungcap`
  ADD PRIMARY KEY (`nhacungcapid`);

--
-- Chỉ mục cho bảng `sanpam`
--
ALTER TABLE `sanpam`
  ADD PRIMARY KEY (`sanphamid`),
  ADD KEY `LoaiSanPhamID` (`loaisanphamid`),
  ADD KEY `DonViTinhID` (`donvitinhid`),
  ADD KEY `NhaCungCapID` (`nhacungcapid`);

--
-- Chỉ mục cho bảng `thuoctinh`
--
ALTER TABLE `thuoctinh`
  ADD PRIMARY KEY (`thuoctinhid`);

--
-- Chỉ mục cho bảng `thuoctinhsanpham`
--
ALTER TABLE `thuoctinhsanpham`
  ADD PRIMARY KEY (`thuoctinhsanphamid`),
  ADD KEY `SanPhamID` (`sanphamid`),
  ADD KEY `ThuocTinhID` (`thuoctinhid`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userid`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  MODIFY `chitietdathangid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `datdichvu`
--
ALTER TABLE `datdichvu`
  MODIFY `datdichvuid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `dichvu`
--
ALTER TABLE `dichvu`
  MODIFY `dichvuid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `dondathang`
--
ALTER TABLE `dondathang`
  MODIFY `dondathangid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `donvitinh`
--
ALTER TABLE `donvitinh`
  MODIFY `donvitinhid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `loaisanpham`
--
ALTER TABLE `loaisanpham`
  MODIFY `loaisanphamid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `media`
--
ALTER TABLE `media`
  MODIFY `mediaid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `nhacungcap`
--
ALTER TABLE `nhacungcap`
  MODIFY `nhacungcapid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `sanpam`
--
ALTER TABLE `sanpam`
  MODIFY `sanphamid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `thuoctinh`
--
ALTER TABLE `thuoctinh`
  MODIFY `thuoctinhid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `thuoctinhsanpham`
--
ALTER TABLE `thuoctinhsanpham`
  MODIFY `thuoctinhsanphamid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `userid` int(11) NOT NULL AUTO_INCREMENT;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  ADD CONSTRAINT `chitietdonhang_ibfk_1` FOREIGN KEY (`SanPhamID`) REFERENCES `sanpam` (`SanPhamID`),
  ADD CONSTRAINT `chitietdonhang_ibfk_2` FOREIGN KEY (`đonathangid`) REFERENCES `dondathang` (`DonDatHangID`);

--
-- Các ràng buộc cho bảng `datdichvu`
--
ALTER TABLE `datdichvu`
  ADD CONSTRAINT `datdichvu_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserId`),
  ADD CONSTRAINT `datdichvu_ibfk_2` FOREIGN KEY (`DichVuID`) REFERENCES `dichvu` (`DichVuID`);

--
-- Các ràng buộc cho bảng `dondathang`
--
ALTER TABLE `dondathang`
  ADD CONSTRAINT `dondathang_ibfk_1` FOREIGN KEY (`taikhoandathangid`) REFERENCES `users` (`UserId`);

--
-- Các ràng buộc cho bảng `loaisanpham`
--
ALTER TABLE `loaisanpham`
  ADD CONSTRAINT `loaisanpham_ibfk_1` FOREIGN KEY (`LoaiSanPhamPID`) REFERENCES `loaisanpham` (`loaisanphamid`);

--
-- Các ràng buộc cho bảng `media`
--
ALTER TABLE `media`
  ADD CONSTRAINT `media_ibfk_1` FOREIGN KEY (`SanPhamID`) REFERENCES `sanpam` (`SanPhamID`),
  ADD CONSTRAINT `media_ibfk_2` FOREIGN KEY (`DichVuID`) REFERENCES `dichvu` (`DichVuID`);

--
-- Các ràng buộc cho bảng `sanpam`
--
ALTER TABLE `sanpam`
  ADD CONSTRAINT `sanpam_ibfk_1` FOREIGN KEY (`LoaiSanPhamID`) REFERENCES `loaisanpham` (`loaisanphamid`),
  ADD CONSTRAINT `sanpam_ibfk_2` FOREIGN KEY (`DonViTinhID`) REFERENCES `donvitinh` (`donvitinhid`),
  ADD CONSTRAINT `sanpam_ibfk_3` FOREIGN KEY (`NhaCungCapID`) REFERENCES `nhacungcap` (`NhaCungCapID`);

--
-- Các ràng buộc cho bảng `thuoctinhsanpham`
--
ALTER TABLE `thuoctinhsanpham`
  ADD CONSTRAINT `thuoctinhsanpham_ibfk_1` FOREIGN KEY (`ThuocTinhID`) REFERENCES `thuoctinh` (`ThuocTinhID`),
  ADD CONSTRAINT `thuoctinhsanpham_ibfk_2` FOREIGN KEY (`sanphamid`) REFERENCES `sanpam` (`SanPhamID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
