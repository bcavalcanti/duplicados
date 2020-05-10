package br.com.umdia;

import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA_256;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws Exception {
        
        String baseDir = "C:\\Users\\bcava\\Pictures\\";
        String baseDirDuplicado = "C:\\Users\\bcava\\Pictures\\duplicados";
        
        LocalDateTime inicio = LocalDateTime.now();
        HashMap<String, File> arquivos = new HashMap<>();
        Files.walk(Paths.get(baseDir))
            .parallel()
            .forEach(p -> {
                String hdigest = null;
                try {
                    File file = new File(p.toString());
                    if (file.isFile()) {
                        hdigest = new DigestUtils(SHA_256).digestAsHex(file);
                        if (!arquivos.containsKey(hdigest)) {
                            arquivos.put(hdigest, file);
                        } else {
                            String pathDuplicado = file.getAbsolutePath().replace(baseDir, "");
                            Path arqDirArqDuplicado = Paths.get(baseDirDuplicado, pathDuplicado);
                            try {
                                Files.createDirectories(arqDirArqDuplicado);
                            }catch (Exception ex){
                                //System.out.println(String.format(format, args));
                            }
                            Files.move(file.toPath(), Paths.get(baseDirDuplicado, pathDuplicado), REPLACE_EXISTING);
                        
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        LocalDateTime fim = LocalDateTime.now();
        System.out.println(
                String.format("Tempo de processamento foi de %s minutos.", inicio.until(fim, ChronoUnit.MINUTES)));
    }
}
