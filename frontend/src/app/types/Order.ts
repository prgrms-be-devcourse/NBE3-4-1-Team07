export interface Order {
  id: number;
  email: string;
  address: string;
  postalCode: string;
  state: string;
  totalPrice: number;
  orderDate: string;
}

export interface OrderRequestDto {
  orders: Order[];
}
