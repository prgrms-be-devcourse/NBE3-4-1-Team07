export interface Product {
  id: number;
  name: string;
  price: number;
  description: string;
  quantity: number;
  imgPath: string;
}

export interface ProductResponseDto {
  products: Product[];
}

