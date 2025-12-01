package com.ms.app.config;

import com.ms.app.model.*;
import com.ms.app.repository.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final UsersRepository userRepository;
    private final ArtistRepository artistRepository;
    private final GenreRepository genreRepository;
    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;
    // Thêm Repository bảng trung gian để liên kết Playlist và Song
    private final PlaylistSongRepository playlistSongRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) seedUsers();
        if (genreRepository.count() == 0) seedGenres();
        if (artistRepository.count() == 0) seedArtists();
        if (songRepository.count() == 0) seedSongs();
        if (playlistRepository.count() == 0) seedPlaylists();

        // Thêm bước seed bài hát vào playlist (chạy cuối cùng)
        if (playlistSongRepository.count() == 0) seedPlaylistSongs();

        System.out.println(">>> SEEDING DATA COMPLETED");
    }

    private void seedUsers() {
        System.out.println("Seeding Users...");
        List<User> users = Arrays.asList(
                createUser("admin", "admin@music.com", "ROLE_ADMIN", "Admin", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764505444/music_app_images/kd9vquyskyxr9v3yfg8z.png"),
                createUser("nam_nguyen", "nam@gmail.com", "ROLE_USER", "Nam Nguyen", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764502008/music_app_images/vszstacjnobhgoum4bti.jpg")
        );
        userRepository.saveAll(users);
    }

    private User createUser(String username, String email, String role, String displayName, String avatar) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRole(role);
        user.setDisplayname(displayName);
        user.setAvatarUrl(avatar);
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }

    private void seedGenres() {
        System.out.println("Seeding Genres...");
        List<GenreData> dataList = Arrays.asList(
                new GenreData("Pop", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764504749/Pop_gxxak7.jpg", "Nhạc Pop thịnh hành."),
                new GenreData("EDM", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764504748/EDM_kq5ji7.jpg", "Electronic Dance Music."),
                new GenreData("Hip-Hop", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764504749/Hip-Hop_ztsc0k.jpg", "Văn hóa đường phố & Rap."),
                new GenreData("R&B", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764504749/RB_euclcj.jpg", "Rhythm and Blues."),
                new GenreData("Indie", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764504748/Indie_qphzp9.jpg", "Dòng nhạc độc lập."),
                new GenreData("Rock", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764504749/Rock_bljxub.jpg", "Giai điệu mạnh mẽ."),
                new GenreData("Lo-Fi", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764504748/Lofi_n6oj6z.jpg", "Nhạc chill học tập."),
                new GenreData("House", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764504749/House_ijfvja.jpg", "Nhịp điệu dồn dập."),
                new GenreData("Jazz", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764504748/Jazz_ikzs3a.jpg", "Sự ngẫu hứng tinh tế."),
                new GenreData("Classical", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764504748/Classic_uyeiou.jpg", "Nhạc cổ điển.")
        );

        List<Genre> genres = new ArrayList<>();
        for (GenreData d : dataList) {
            Genre g = new Genre();
            g.setName(d.getName());
            g.setDescription(d.getDescription());
            g.setImageUrl(d.getSrc());
            genres.add(g);
        }
        genreRepository.saveAll(genres);
    }

    private void seedArtists() {
        System.out.println("Seeding Artists...");
        List<ArtistData> dataList = getArtistDataList();

        List<Artist> artists = new ArrayList<>();
        for (ArtistData d : dataList) {
            Artist a = new Artist();
            a.setName(d.getName());
            a.setBio(d.getBio());
            a.setAvatarUrl(d.getAvatarUrl());
            a.setCreatedAt(LocalDateTime.now());
            artists.add(a);
        }
        artistRepository.saveAll(artists);
    }

    private void seedSongs() {
        System.out.println("Seeding Songs...");
        User user = userRepository.findByEmail("nam@gmail.com").orElse(null);
        if (user == null) return;

        List<Artist> artists = artistRepository.findAll();
        List<Genre> genres = genreRepository.findAll();
        Random random = new Random();
        List<SongData> songDataList = getSongDataList();

        List<Song> songs = new ArrayList<>();

        for (int i = 0; i < songDataList.size(); i++) {
            SongData data = songDataList.get(i);
            // Lấy artist tương ứng, nếu hết list thì quay vòng
            Artist artist = artists.get(i % artists.size());
            Genre genre = genres.get(random.nextInt(genres.size()));

            Song s = new Song();
            s.setTitle(data.getTitle());
            s.setDescription("Một bài hát tuyệt vời của " + artist.getName());
            s.setArtist(artist);
            s.setUser(user);
            s.setGenre(genre);
            s.setFileUrl(data.getUrl());
            s.setCoverUrl(artist.getAvatarUrl());
            s.setDuration(240.0);
            s.setStatus("ACTIVE");
            s.setCreatedAt(LocalDateTime.now());
            songs.add(s);
        }
        songRepository.saveAll(songs);
    }

    private void seedPlaylists() {
        System.out.println("Seeding Playlists...");
        User user = userRepository.findByEmail("nam@gmail.com").orElse(null);
        if (user == null) return;

        List<PlaylistData> dataList = Arrays.asList(
                new PlaylistData("Nhạc Chill Cuối Tuần", "Tuyển tập thư giãn.", true, "https://res.cloudinary.com/dtnonjckn/image/upload/v1764515706/chill_d6a3kf.jpg"),
                new PlaylistData("Gym Motivation", "Nhạc tập luyện cực căng.", false, "https://res.cloudinary.com/dtnonjckn/image/upload/v1764517129/Gaming_s5bkqs.jpg"),
                new PlaylistData("Code Music 2025", "Nhạc không lời code.", true, "https://res.cloudinary.com/dtnonjckn/image/upload/v1764517129/Code_n7h6gj.jpg"),
                new PlaylistData("Nhạc Buồn Tâm Trạng", "Cho những ngày mưa.", false, "https://res.cloudinary.com/dtnonjckn/image/upload/v1764517131/Sad_xl6ns1.jpg"),
                new PlaylistData("Party Nonstop", "Quẩy lên nào!", true, "https://res.cloudinary.com/dtnonjckn/image/upload/v1764517130/Party_eesnpt.jpg")
        );

        List<Playlist> playlists = new ArrayList<>();
        for (PlaylistData d : dataList) {
            Playlist p = new Playlist();
            p.setName(d.getName());
            p.setDescription(d.getDescription());
            p.setUser(user);
            p.setIsPublic(d.isPublic());
            p.setCoverUrl(d.getCoverUrl());
            p.setCreatedAt(LocalDateTime.now());
            playlists.add(p);
        }
        playlistRepository.saveAll(playlists);
    }

    // --- MỚI: HÀM THÊM BÀI HÁT VÀO PLAYLIST ---
    private void seedPlaylistSongs() {
        System.out.println("Seeding Playlist Songs (2 songs per playlist)...");
        List<Playlist> playlists = playlistRepository.findAll();
        List<Song> songs = songRepository.findAll();

        if (playlists.isEmpty() || songs.size() < 2) return;

        Random random = new Random();
        List<PlaylistSong> linksToSave = new ArrayList<>();

        for (Playlist playlist : playlists) {
            // Chọn ngẫu nhiên 2 bài hát khác nhau
            Song song1 = songs.get(random.nextInt(songs.size()));
            Song song2;
            do {
                song2 = songs.get(random.nextInt(songs.size()));
            } while (song2.getId().equals(song1.getId())); // Đảm bảo không trùng

            // Tạo liên kết 1
            PlaylistSong link1 = new PlaylistSong();
            link1.setPlaylist(playlist);
            link1.setSong(song1);
            link1.setAddedAt(LocalDateTime.now());
            linksToSave.add(link1);

            // Tạo liên kết 2
            PlaylistSong link2 = new PlaylistSong();
            link2.setPlaylist(playlist);
            link2.setSong(song2);
            link2.setAddedAt(LocalDateTime.now().plusMinutes(1)); // Thêm sau 1 chút
            linksToSave.add(link2);
        }
        playlistSongRepository.saveAll(linksToSave);
    }

    // --- DATA HELPERS ---

    private List<ArtistData> getArtistDataList() {
        return Arrays.asList(
                new ArtistData("Lorien Testard", "Nhạc sĩ tài năng với những giai điệu Epic.", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764507412/Lorien_lso8ko.jpg"),
                new ArtistData("Alan Walker", "DJ và nhà sản xuất âm nhạc người Anh gốc Na Uy.", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764507407/Alan_Walker_ibxcfs.jpg"),
                new ArtistData("TheFatRat", "Nổi tiếng với dòng nhạc Glitch Hop và Gaming music.", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764507416/TFR_scc87w.jpg"),
                new ArtistData("Justin Bieber", "Ngôi sao nhạc Pop toàn cầu.", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764507411/Justin_jdmjr4.webp"),
                new ArtistData("Shawn Mendes", "Ca sĩ kiêm nhạc sĩ người Canada.", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764507413/Shawn_zutjxt.jpg"),
                new ArtistData("Charlie Puth", "Được biết đến với khả năng Pitch Perfect.", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764507408/CP_caadxv.jpg"),
                new ArtistData("Linkin Park", "Ban nhạc Rock huyền thoại.", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764515567/Link_rbp4mi.jpg"),
                new ArtistData("DAOKO", "Rapper và ca sĩ nữ Nhật Bản.", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764507409/DAOKO_g4j3px.jpg"),
                new ArtistData("Kenshi Yonezu", "Ca sĩ J-Pop nổi tiếng với bài Lemon.", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764507411/Kenshi_e9xfsf.webp"),
                new ArtistData("Sơn Tùng M-TP", "Hoàng tử mưa, V-Pop Star.", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764507412/MTP_eiklvy.jpg"),
                new ArtistData("Đen Vâu", "Rapper tử tế.", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764507417/DV_zwkqmr.png"),
                new ArtistData("Chillies", "Ban nhạc Indie được yêu thích.", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764507409/Chill_zi3cgk.jpg"),
                new ArtistData("BTS", "Nhóm nhạc nam Hàn Quốc toàn cầu.", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764507408/BTS_vmn7n1.png"),
                new ArtistData("Blackpink", "Nhóm nhạc nữ K-Pop hàng đầu.", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764507407/BP_g11jb6.png"),
                new ArtistData("Taylor Swift", "Công chúa nhạc Country/Pop.", "https://res.cloudinary.com/dtnonjckn/image/upload/v1764507415/Taylor_tj9de6.webp")
        );
    }

    private List<SongData> getSongDataList() {
        return Arrays.asList(
                new SongData("Une vie taimer", "https://res.cloudinary.com/dtnonjckn/video/upload/v1764516721/Clair_Obscur_Expedition_33_Original_Soundtrack_60_Une_vie_%C3%A0_t_aimer_rnimpm.mp3"),
                new SongData("Faded", "https://res.cloudinary.com/dtnonjckn/video/upload/v1764516712/Alan_Walker_Faded_ngo7mz.mp3"),
                new SongData("Unity", "https://res.cloudinary.com/dtnonjckn/video/upload/v1764516711/TheFatRat_Unity_hqy8va.mp3"),
                new SongData("Peaches", "https://res.cloudinary.com/dtnonjckn/video/upload/v1764516710/Justin_Bieber_Peaches_ft.Daniel_Caesar_Giveon_ympeew.mp3"),
                new SongData("Stitches", "https://res.cloudinary.com/dtnonjckn/video/upload/v1764516709/Shawn_Mendes_Stitches_Official_Music_Video_lffnx6.mp3"),
                new SongData("Attention", "https://res.cloudinary.com/dtnonjckn/video/upload/v1764516708/Charlie_Puth_Attention_Official_Video_xgdz0u.mp3"),
                new SongData("Numb", "https://res.cloudinary.com/dtnonjckn/video/upload/v1764516708/Numb_o0jhqo.mp3"),
                new SongData("Uchiage Hanabi", "https://res.cloudinary.com/dtnonjckn/video/upload/v1764516717/Uchiage_Hanabi_syazyu.mp3"),
                new SongData("Lemon", "https://res.cloudinary.com/dtnonjckn/video/upload/v1764516716/Lemon_kkutty.mp3"),
                new SongData("Lạc Trôi", "https://res.cloudinary.com/dtnonjckn/video/upload/v1764516714/Lac_Troi_qaqn0s.mp3"),
                new SongData("Mang Tiền Về Cho Mẹ", "https://res.cloudinary.com/dtnonjckn/video/upload/v1764516719/Loi_Nho_z9baie.mp3"),
                new SongData("Vùng Ký Ức", "https://res.cloudinary.com/dtnonjckn/video/upload/v1764516719/Loi_Nho_z9baie.mp3"),
                new SongData("Dynamite", "https://res.cloudinary.com/dtnonjckn/video/upload/v1764516713/BTS_Dynamite_idjupt.mp3"),
                new SongData("How You Like That", "https://res.cloudinary.com/dtnonjckn/video/upload/v1764516711/How_you_like_that_actgsx.mp3"),
                new SongData("Love Story", "https://res.cloudinary.com/dtnonjckn/video/upload/v1764516710/Taylor_Swift_Love_Story_zy28j1.mp3")
        );
    }

    // --- INNER CLASSES (DTO) ---
    @Getter @AllArgsConstructor
    static class GenreData {
        private String name;
        private String src;
        private String description;
    }

    @Getter @AllArgsConstructor
    static class ArtistData {
        private String name;
        private String bio;
        private String avatarUrl;
    }

    @Getter @AllArgsConstructor
    static class PlaylistData {
        private String name;
        private String description;
        private boolean isPublic;
        private String coverUrl;
    }

    @Getter @AllArgsConstructor
    static class SongData {
        private String title;
        private String url;
    }
}