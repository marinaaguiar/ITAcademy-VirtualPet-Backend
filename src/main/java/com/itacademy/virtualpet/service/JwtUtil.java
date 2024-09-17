package com.itacademy.virtualpet.service;

import com.itacademy.virtualpet.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private String SECRET_KEY =
            "MIIJQgIBADANBgkqhkiG9w0BAQEFAASCCSwwggkoAgEAAoICAQCyxZJO2QyjeKBi" +
            "XREoE0DSvAACVV6HNBbn0/GoGPe2wJMA49rkNYwDye3aT2LAHZMX6lKD9Bxbrupr" +
            "IVqZ8NTZBmNCBr4uO/Z5yRHg8u2Kx4xJhQMq9Ue8eoVyWyHUrB6CzuKW9dYfRcYZ" +
            "1svY5+Rpg/wWnxhdlhe4PG2U2TJz/6nDmhb0qNgpnM+Z0g71kIM0K9SS10LvG6Xt" +
            "3QBB2v5qWdRBNDpswfrdIzTw23/0VOxLZoMmmf/eOfl6INk8ZCMqFjhEMeElDuAq" +
            "wyocNabdos9nNTaW5pC5Ltns54/JDf9Ttem+Ls6Ji2sWhJlF3awAvsitukk66F+c" +
            "XG16Ayx0UJsRyTF/Q0w7uMiNuShLFfKHwH3N0b5+grVWd+DqHz94+7jR5Hh64uPn" +
            "csiq1VVf0wlrr7uedj5euihnMyEnMLTr7iLyngc/IXYXOzSVE5P5noc9+5XLOm3R" +
            "oOvyi4I/o/Y5MCD5ssYmVP0vNgdpuKLmjwRdMcw2SmDma2xD21s8HFcktC0pkbX2" +
            "LLlTK1zCFPxXSC5JZmYxqUyXBughNGnYW2MtPsAXjrplP3H4T4ECKZEYZ9kYRPUJ" +
            "3nT26VGDplg4Wlhiz8Mpitt8zFAoYUdUluGFB0EBzF6FhyREkYwhNHLIfzbLpKlc" +
            "MvsTT3lBAk3epvkG+vS1rbdNhwMU0QIDAQABAoICABSNSpvmb4HGjxenz95d0kS3" +
            "yL977X1ZI23862nyDt5TZsMCLuqv7jwMRZwkprtj2wJzlY9ma7Etm3T79WS/r1SQ" +
            "263eRRRfH9+R2kyUWPMsrCfSkxHn91qHclp6eBnxPKO4oYuN0RMwzAyx6SbzR8rK" +
            "mdtG6H/vzqPHTKjBTH/R60QZrZxsYcds59/s6nvYJIFFra9A8Ii5ErsU/l/Ty45R" +
            "6+kgFjxlE5BDKSWscloWPkEP1LGGIEh9vTzJVHgRIgrHWaobWuEOA/7eh3ONRnTx" +
            "7+qTT0aLVKV5aaPG3jyUr58ByZCr5GIjJUlGLrG9wXWneT4hraQITV+WL57IiR5X" +
            "4xWjIS2tMHu2nSp6sbGnbp1MlGQ1OqS5tlSSjBXS5Sqvmu4pmjioaAL7i4mFtYVA" +
            "mUQ3GMxFImsQMTZNf1PnSip1TlSF8Ci6J0nmnNffHL/qGUpafTxyAJVny5bCca1P" +
            "iVMT9QI1UzZRh1NDG7/Z8pmoVV3JxjneKKuHu0xy1uzzQO+hFTmT/+ZYICCFVsEK" +
            "pWJZexsu5CDKuEVNFTbKwITjz3+Iu1qnf/LWSFxV8zUBHfznMSxljoGqUurHTsD/" +
            "Zo027/Oxua1troHeQheV8KhZRq6TOfSGuY6MlZVZg5O80nVOEcIaNZJGLUsix4vd" +
            "qMtw7VbG3nHfk24UTsb9AoIBAQDdjJWZlpZceDN5o+Fk6cg+aNVOQ7s5/qAL37yQ" +
            "aYbAyuRr6n+immA6tOyR7Svx+kF6XppSdsHw3+4bxGuaIvgrX+SUh9SI84KL/Gyo" +
            "gWHAKYW65yjEpF+gKw2r+ZlGchFNPal8WI5DIbV3+226U4GV3uPIqQjX/uLJrCzz" +
            "9Qpj4KXTnVG0C54cz4QWi1TiFcz+tpd1FfV2h0Griw5l2RZAzy+4oFCeKI4pYb+Q" +
            "I1gRh0B5Ibqc/6banLHyqViVcaYDbGdrbiVQPig11b/G2kocr+gNz3yxMTA+EgM4" +
            "R9HQBSm9qfFuNEUQEymXWSNDe4GKQlZ/hIZqqB7FqPz1WwIdAoIBAQDOkhtxXhzI" +
            "RsVzZbRGtwls2IbsDJDrZEykr570PssdDDjcx48enFbLQp1Z/ShAnbVkaiE1jE4W" +
            "OE0blzsE7bmA239T8FLVM1BRns12prbMIK07guoLWsE+tKV7hqCoIx+pU2VdSdzE" +
            "doo8oAH53QE2py1CHxUCvbMsFQDxbXvN7HAjM2tIEVlDZQ25B4zvrQgm8yPaNwey" +
            "1eJylnli67FYBBrrLCYA+7mmXCk3BIhT8OgmY/dMs+2ZCM0vfE9Yo4kgoVUD6GjQ" +
            "c4+JGJyN/poHunRCXF+UB0gOnVVxjowMpZ1qstoW7ayckNAN6Edinu9272/egu1w" +
            "jCvf4eIMAh9FAoIBAQCkJrvZVrgFv5H0OLaR/J2/kBErsgeRCtYuthGStn1/b1Ub" +
            "mF4FbniKRu9Zl+8NlSzarCmPnCpKfMCBlmFbjTlvedd1LccNyc8aRWeE2ohAxwMs" +
            "3oVYXan0lLHGkGM+zNmA74Uaesq9dsZsJcDBY4oKL2F6lbPVxibm4klUYGTYZ+S2" +
            "DEQzMMBv/FmUEpxL/3cp6sQBnsmGjuTbY2bBk71nO5vSIgZZiRb8Fk7HT0i7hAkD" +
            "Nzh7hvhr1doLP+pAoDovIyzlbnD7KXLFoKA9iBONedBoGyn6Z8Wk19fG5X/Rr2Gk" +
            "A7y8uMHLDUetuf/kyifK9yf7x4eIkxRpw7HZYRsJAoIBAChUWC1C/YWQY55K/e5M" +
            "b/ejMnxFW8nJNDl23Ca9V7RdHYDlKSkFxuA6KjRWe5RZ9f8bxU4KDXjDFzLKzMgC" +
            "jZP9xpchtVpQgV7eDSkcFVY3DvsubPk4h7/xTtmWCCmradQKJyEWeZaVuhlp99jL" +
            "cCA2dVySauy1EpUJqLx57pDel2oX8NsKIA5LXWmBct+cPZo1IAvLcKJWpbZPcHWC" +
            "pIYVjxfY77aDbtRu69EE/oS85OYR4EdGXZ3iHQUnVOpuSyhmtw1jQ4tm9GAEwRPr" +
            "4DCgE3CdTj+9SDEqQHNCLE+PyfL55iBR2MBceXItpjo343Kp/oJHt4mgpDQ6ioJj" +
            "q60CggEAGXL88/9JUah+FIAx21NmHpBfopryqQ3g7AjykozzbfgGMNRPm7134+nH" +
            "iJZGXD1Jr+Qo2a5ySueFh3k127D7UO2Qj7rtCg9eX5kNTgv87KY7bWsVOL/lF/n4" +
            "HPBhguB5YFVS7xYuGG4kq4C08fF1aUzBeXFc9wlnaKHHS1oR0lFeMLzeqTm06pVV" +
            "sOrMDUHQ3/TV7EsELwZkQZp7lFQztMUuE8oHW+i2KkVM0FmhOUy/GpSg5xkm+d/v" +
            "qOv7kjjiXmwdi4u/2WzUjSa/6srenbdH+xOr9mj5+Vsf9UtSZsBsFVAY1v8I3CF+" +
            "3543pkMSv0PHYsu0Xs+drB41q3SSXQ==";

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, user.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 100)) // Token valid for 10 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        System.out.println("TOKEN: " + token);
        return token;
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }
}
