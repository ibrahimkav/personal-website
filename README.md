**Proje Dokümanı: Spring Boot ve Angular ile KŞisel Web Sitesi**

---

## **Proje Amacı**
Bu proje, bireysel portföy ve kariyer geçmişi sergilemek amacıyla, Spring Boot ve Angular teknolojileri kullanılarak geliştirilecek bir kışisel web sitesidir. Kullanıcılar bu sitede kişinin hakkında bilgi edinebilir, projelerini inceleyebilir ve iletişime geçebilir. Aynı zamanda, bir admin paneli ile içerikler kolayca güncellenebilir.

---

## **Teknoloji Yığını**

### **Backend (Spring Boot)**
- **Java 17 veya üstü**
- **Spring Boot Starter Web**: RESTful API geliştirmek için.
- **Spring Security**: Admin paneli için JWT tabanlı kimlik doğrulama.
- **Hibernate & JPA**: Veritabanı işlemleri.
- **H2 (geliştirme)** veya **PostgreSQL/MySQL (canlı)**: Veritabanı seçenekleri.
- **MapStruct veya Lombok**: DTO dönüşümleri ve kod sadeleştirme.
- **Spring Boot Starter Mail**: Opsiyonel olarak e-posta gönderimi.

### **Frontend (Angular)**
- **Angular CLI**
- **Angular Material veya TailwindCSS**: Modern tasarımlar.
- **RxJS**: Reaktif veri akışı.
- **ngx-translate**: Çoklu dil desteği.
- **Angular Router**: Sayfa yönlendirmeleri.

### **Diğer Araçlar**
- **Docker**: Containerize etme.
- **GitHub Actions veya Jenkins**: CI/CD entegrasyonu.
- **Google Analytics veya Matomo**: Ziyaretçi analitikleri.

---

## **Site Özellikleri**

### **1. Kullanıcı Tarafı Sayfaları**

#### **a. Ana Sayfa (Home)**
- Kendi adınızı ve başlığınızı ("Yazılımcı", "Veri Bilimci" vb.) sergileyen bir bölüm.
- LinkedIn, GitHub gibi sosyal medya linkleri.
- "Hakkımda", "Projelerim" ve "İletĿişim" gibi sayfalara hızlı erişim butonları.

#### **b. Hakkımda (About Me)**
- **Eğitim Geçmişi**: Mezun olunan okullar, bölüm, yıl bilgisi.
- **İş Geçmişi**: Çalışılan şirketler, pozisyonlar, tarihler ve kısa açıklamalar.

#### **c. Projelerim (Projects)**
- GitHub API entegrasyonu ile projelerin otomatik listelenmesi.
- Proje adı, açıklaması, kullanılan teknolojiler ve repo linkleri.
- Öne çıkan projeler için ayrı bir bölüm.

#### **d. İletĿişim (Contact)**
- Basit bir iletişim formu (Ad, E-posta, Mesaj).
- Formdan gelen bilgiler backend tarafından e-posta olarak gönderilebilir.

### **2. Admin Paneli**
- JWT tabanlı oturum açma sistemi.
- Eğitim ve iş bilgilerini ekleme/düzenleme/silme.
- GitHub API'den projeleri kontrol etme ve manuel proje ekleme.
- Ziyaretçi mesajlarını listeleme ve yanıtlama.

---

## **Backend Endpoint’leri**

| **HTTP Metodu** | **Endpoint**               | **Açıklama**                         |
|------------------|----------------------------|---------------------------------------|
| `GET`           | `/api/about`              | Hakkımda bilgilerini getirir.        |
| `PUT`           | `/api/about`              | Hakkımda bilgilerini günceller.      |
| `GET`           | `/api/experience`         | İş geçmişi bilgilerini getirir.      |
| `POST`          | `/api/experience`         | Yeni iş geçmişi ekler.               |
| `DELETE`        | `/api/experience/{id}`    | İş geçmişini siler.                  |
| `GET`           | `/api/projects`           | Projeleri getirir (GitHub veya manuel). |
| `POST`          | `/api/projects`           | Yeni proje ekler.                     |
| `DELETE`        | `/api/projects/{id}`      | Projeyi siler.                        |
| `POST`          | `/api/contact`            | İletĿişim formu mesajlarını kaydeder. |
| `GET`           | `/api/cv`            | CV'yi getirir. |


---

## **Frontend Modül Yapısı**

### **Angular Modülleri**
1. **Shared Module**
   - Ortak bileşenler: Header, Footer, Card.

2. **Core Module**
   - Servisler: API servisleri, Guard'lar (auth gibi).

3. **Feature Modules**
   - **Home**: Ana sayfa.
   - **About**: Hakkımda bilgileri.
   - **Projects**: Proje listesi.
   - **Contact**: İletĿişim formu.
   - **Admin**: Yönetim paneli.

### **Tema ve Tasarım**
- **Angular Material** ile responsive ve modern bir tasarım.
- **Karanlık/Aydınlık Tema** seçenekleri.
- CSS animasyonları ve çekici bir "timeline" gösterimi.

---

## **Ekstralar**

### **Ziyaretçi Deneyimini Artıracak Özellikler**
- **Çoklu Dil Desteği**: ngx-translate kullanılabilir.
- **CV İndirme**: PDF formatında indirilebilir bir özgeçmiş.
- **Proje Demo Linkleri**: GitHub linki yanında proje demolarını da ekleyin.
- **QR Kod**: Sosyal medya profilleriniz için.

### **Admin Paneli İyileştirmeleri**
- Markdown destekli metin editörü.
- Ziyaretçi istatistikleri (Google Analytics veya Matomo).

---

Bu doküman, web sitesinin geliştirilmesi ve yönetimi için temel bir yol haritası sunar. Gereksinimlere göre yeni özellikler eklenebilir ya da mevcutlar optimize edilebilir.

