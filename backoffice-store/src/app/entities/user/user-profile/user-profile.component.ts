import { Component, OnInit } from '@angular/core';
import { User } from '../model/user.model';


@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  user: User | null = null;

  constructor() {}

  ngOnInit(): void {
    // Obtener la información del usuario del almacenamiento local
    const user = localStorage.getItem('user');
    if (user) {
      this.user = JSON.parse(user);
    }
  }
}
