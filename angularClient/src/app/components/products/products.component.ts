import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/api/service/product.service';
import { AuthService } from 'src/app/api/service/auth.service';
import { Router } from '@angular/router';
import { Product } from 'src/app/api/entity/Product';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.less'],
})
export class ProductsComponent implements OnInit {
  constructor(
    private productService: ProductService,
    private authService: AuthService,
    private router: Router
  ) {}
  А;
  data: Product[] = [];
  displayedColumns: string[] = ['prodName', 'prodDesc', 'prodPrice'];
  isLoadingResults = true;

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts(): void {
    this.productService.getProducts().subscribe(
      (products) => {
        this.data = products;
        console.log(this.data);
        this.isLoadingResults = false;
      },
      (err) => {
        console.log(err);
        this.isLoadingResults = false;
      }
    );
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['login']);
  }
}
