import { Product } from "./Product";

export interface Order {
  id: number;
  email: string;
  address: string;
  postalCode: string;
  state: string;
  totalPrice: number;
  orderDate: string;
}

export interface OrderResponseDto {
  orders: Order[];
}

export interface OrderRequestDto {
  email: string;
  address: string;
  postalCode: string;
  totalPrice: number;
  products: {
    productId: number;
    quantity: number;
  }[];
}

export interface OrderDetailResponseDto {
  products: {
    name: string;
    price: number;
    quantity: number;
    imgPath: string;
  }[];
}