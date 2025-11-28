package com.ms.app.service;
import com.ms.app.dto.SongDTO;
import com.ms.app.model.Artist;
import com.ms.app.model.Genre;
import com.ms.app.model.Song;
import com.ms.app.model.User;
import com.ms.app.repository.ArtistRepository;
import com.ms.app.repository.GenreRepository;
import com.ms.app.repository.SongRepository;
import com.ms.app.repository.UsersRepository;
import com.ms.app.service.CloudinaryService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class SongServiceImpl implements SongService {
    private final SongRepository songRepo;
    private final CloudinaryService cloudinaryService;
    private final ArtistRepository artistRepo;
    private final GenreRepository genreRepo;
    private final UsersRepository userRepo;
    public SongServiceImpl(
            SongRepository songRepo,
            CloudinaryService cloudinaryService,
            ArtistRepository artistRepo,
            GenreRepository genreRepo,
            UsersRepository userRepo)
    {
        this.songRepo = songRepo;
        this.cloudinaryService = cloudinaryService;
        this.artistRepo = artistRepo;
        this.genreRepo = genreRepo;
        this.userRepo = userRepo;
    }
    @Override
    public Song create(SongDTO dto, MultipartFile mp3File, MultipartFile imageFile) throws IOException {
        Song song = new Song();
        if (dto.getArtistId() != null) {
            Artist artist = artistRepo.findById(dto.getArtistId())
                    .orElseThrow(() -> new RuntimeException("Artist not found"));
            song.setArtist(artist);
        }
        if (dto.getGenreId() != null) {
            Genre genre = genreRepo.findById(dto.getGenreId())
                    .orElseThrow(() -> new RuntimeException("Genre not found"));
            song.setGenre(genre);
        }
        //Lấy UserID dựa trên email trong context hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof User current_User) {
            song.setUser(current_User);
        } else {
            String email = authentication.getName();
            User current_User = userRepo.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found: " + email));
            song.setUser(current_User);
        }
        //Set file url và các thông tin khác của MP3
        if (mp3File != null && !mp3File.isEmpty()) {
            Map uploadResult = cloudinaryService.uploadFile(mp3File);
            song.setFileUrl((String) uploadResult.get("secure_url"));
            song.setDuration((Double) uploadResult.get("duration"));
        }
        if (imageFile != null && !imageFile.isEmpty()) {
            Map uploadResult = cloudinaryService.uploadFile(imageFile);
            song.setCoverUrl((String) uploadResult.get("secure_url"));
        }
        else {
            song.setCoverUrl("https://res.cloudinary.com/dvq3h3h3h/image/upload/v1724244000/default_cover.jpg");
        }
        song.setTitle(dto.getTitle());
        song.setDescription(dto.getDescription());
        song.setCreatedAt(LocalDateTime.now());
        song.setStatus("ACTIVE");
        return songRepo.save(song);
    }
    public void delete(Long id) {
        Song song = songRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found"));
        song.setStatus("DELETED");
        songRepo.save(song);
    }
    public Song findById(Long id) {
        return songRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found"));
    }
    @Override
    public List<Song> findAll() {
            return songRepo.findAll();
    }
}