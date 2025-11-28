package com.ms.app.config;

import com.ms.app.model.*;
import com.ms.app.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final UsersRepository userRepository;
    private final ArtistRepository artistRepository;
    private final GenreRepository genreRepository;
    private final PlaylistRepository playlistRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Kiểm tra xem dữ liệu đã tồn tại chưa để tránh tạo trùng lặp
        if (userRepository.count() == 0) {
            seedUsers();
        }

        if (artistRepository.count() == 0) {
            seedArtists();
        }

        if (genreRepository.count() == 0) {
            seedGenres();
        }

        if (playlistRepository.count() == 0) {
            seedPlaylists();
        }

        System.out.println(">>> SEEDING DATA COMPLETED");
    }

    private void seedUsers() {
        System.out.println("Seeding Users...");

        // Tạo Admin
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@music.com");
        admin.setPassword(passwordEncoder.encode("123456")); // Mật khẩu luôn phải mã hóa
        admin.setRole("ROLE_ADMIN");
        admin.setAvatarUrl("https://ui-avatars.com/api/?name=Admin");
        admin.setCreatedAt(LocalDateTime.now());

        // Tạo User thường
        User user = new User();
        user.setUsername("nam_nguyen");
        user.setEmail("nam@gmail.com");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRole("ROLE_USER");
        user.setAvatarUrl("https://ui-avatars.com/api/?name=Nam+Nguyen");
        user.setCreatedAt(LocalDateTime.now());
        userRepository.saveAll(Arrays.asList(admin, user));
    }

    private void seedArtists() {
        System.out.println("Seeding Artists...");
        Artist sonTung = new Artist();
        sonTung.setName("Sơn Tùng M-TP");
        sonTung.setBio("Hoàng tử mưa, ca sĩ nhạc Pop hàng đầu Việt Nam.");
        sonTung.setAvatarUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/e/e0/S%C6%A1n_T%C3%B9ng_M-TP_1_%282017%29.png/640px-S%C6%A1n_T%C3%B9ng_M-TP_1_%282017%29.png");
        sonTung.setCreatedAt(LocalDateTime.now());
        Artist denVau = new Artist();
        denVau.setName("Đen Vâu");
        denVau.setBio("Rapper nổi tiếng với những bản nhạc chill và lời ca sâu sắc.");
        denVau.setAvatarUrl("https://upload.wikimedia.org/wikipedia/commons/0/07/Den_Vau_2019.jpg");
        denVau.setCreatedAt(LocalDateTime.now());
        Artist chillies = new Artist();
        chillies.setName("Chillies");
        chillies.setBio("Ban nhạc Indie Pop/Rock được yêu thích.");
        chillies.setAvatarUrl("https://i.scdn.co/image/ab6761610000e5eb9d2d83296068218175d71465");
        chillies.setCreatedAt(LocalDateTime.now());
        artistRepository.saveAll(Arrays.asList(sonTung, denVau, chillies));
    }
    private void seedGenres() {
        System.out.println("Seeding Genres...");

        Genre pop = new Genre();
        pop.setName("V-Pop");
        pop.setDescription("Nhạc Pop Việt Nam thịnh hành.");

        Genre rap = new Genre();
        rap.setName("Rap/Hip-hop");
        rap.setDescription("Nhạc Rap và văn hóa Hip-hop.");

        Genre ballad = new Genre();
        ballad.setName("Ballad");
        ballad.setDescription("Những bản tình ca nhẹ nhàng, sâu lắng.");

        Genre indie = new Genre();
        indie.setName("Indie");
        indie.setDescription("Nhạc độc lập, tự do và phóng khoáng.");

        genreRepository.saveAll(Arrays.asList(pop, rap, ballad, indie));
    }

    private void seedPlaylists() {
        System.out.println("Seeding Playlists...");

        // Cần lấy user ra để gán chủ sở hữu playlist
        User user = userRepository.findByEmail("nam@gmail.com").orElse(null);
        if (user != null) {
            Playlist playlist1 = new Playlist();
            playlist1.setName("Nhạc Chill Cuối Tuần");
            playlist1.setDescription("Tuyển tập những bài hát thư giãn nhất.");
            playlist1.setUser(user); // Mapping @ManyToOne
            playlist1.setIsPublic(true);
            playlist1.setCoverUrl("https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4");
            playlist1.setCreatedAt(LocalDateTime.now());

            Playlist playlist2 = new Playlist();
            playlist2.setName("Gym Motivation");
            playlist2.setDescription("Nhạc sôi động để tập luyện.");
            playlist2.setUser(user);
            playlist2.setIsPublic(false); // Playlist riêng tư
            playlist2.setCoverUrl("https://images.unsplash.com/photo-1534438327276-14e5300c3a48");
            playlist2.setCreatedAt(LocalDateTime.now());

            playlistRepository.saveAll(Arrays.asList(playlist1, playlist2));
        }
    }
}