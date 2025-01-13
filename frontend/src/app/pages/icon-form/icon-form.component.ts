import {Component, OnInit} from '@angular/core';
import {RequestService} from '../../service/request.service';
import {RouterLink, Router} from '@angular/router';

@Component({
  selector: 'app-icon-form',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './icon-form.component.html',
  styleUrl: './icon-form.component.css',
  providers: [RequestService]
})
export class IconFormComponent implements OnInit{
  selectedImage: File | null = null;
  username: string | null = null;
  private url = "api/v1/profile";
  content: any

  constructor(private requestService: RequestService, private router: Router) {
  }

  ngOnInit(): void {
    setTimeout(() => {
      this.username = this.getUsernameFromKeycloak();
      this.checkUserProfile();
    }, 500)
  }

  getUsernameFromKeycloak(): string | null {
    return localStorage.getItem('username');
  }

  checkUserProfile(): void {
    this.requestService.getInfo(this.url + "/username")
      .subscribe(
        (response) => {
          localStorage.setItem('profileId', response.id)
          this.router.navigate(['/'])
        },
        (error) => {
          if (error.status !== 404) {
            console.error('Ошибка проверки профиля', error);
          }
        }
      );
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;

    if (input.files && input.files.length > 0) {
      this.selectedImage = input.files[0];
    }
  }

  save(): void {
    if (this.selectedImage && this.username) {
      this.convertImageToString(this.selectedImage)
        .then((imageString) => {
          const profileData = {
            icon: imageString
          };


          this.saveProfile(profileData);
        })
    }
  }

  skip(): void {
    if (this.username) {
      const profileData = {
        icon: ''
      };
      this.saveProfile(profileData)
    }
  }

  saveProfile(formData: any) {
    this.requestService.postInfo(formData, this.url)
      .subscribe(
        (response) => {
          localStorage.setItem('profileId', response.id)
          this.router.navigate(['/'])
        },
        (error) => console.error('Ошибка сохранения профиля', error)
      );
  }

  convertImageToString(file: File): Promise<string> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.onload = () => resolve(reader.result as string);
      reader.onerror = (error) => reject(error);
      reader.readAsDataURL(file);
    });
  }

  convertStringToImage(dataUrl: string): HTMLImageElement {
    const img = new Image();
    img.src = dataUrl;
    return img;
  }
}
