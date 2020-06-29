package com.ssbc.nmg.dataspider.jwt;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ssbc.nmg.dataspider.Constant;
import com.ssbc.nmg.dataspider.MD5Util;
import com.ssbc.nmg.dataspider.dao.User;
import com.ssbc.nmg.dataspider.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
@Service
public class JwtHelper {
    private static Key key;
    @Autowired
    private UserService userService;

    public String getToken(String username ,String password){
        Wrapper<User> queryWrapper = new QueryWrapper<User>();
        ((QueryWrapper<User>) queryWrapper).eq("account", username);
        ((QueryWrapper<User>) queryWrapper).eq("password", MD5Util.md5( password));

        User user = userService.getOne(queryWrapper);

        if(user!=null){
            // 生成JWT的时间
            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);

            if(key == null){
                key =Keys.secretKeyFor(SignatureAlgorithm.HS256);
            }

            JwtBuilder builder = Jwts.builder()
                    .setId(Constant.JWT_ID)
                    .setHeaderParam("typ", "JWT")    //设置header
                    .setHeaderParam("alg", "HS256")
                    .setIssuedAt(now)     //设置iat
                    .claim("username", user.getUserName())   //设置payload的键值对
                    .claim("password", user.getPassWord())
                    .setIssuer("ssbc")
                    .signWith( key);    //签名，需要算法和key

            // 设置过期时间
            if (Constant.JWT_TTL >= 0) {
                long expMillis = nowMillis + Constant.JWT_TTL;
                Date exp = new Date(expMillis);
                builder.setExpiration(exp);
            }

            String jwt = builder.compact();
            return jwt;
        }
        return "";
    }



    public boolean Identity( String token){
        if(key == null){
            key =Keys.secretKeyFor(SignatureAlgorithm.HS256);
        }
        String name =null;
        String pwd = null;

        try {
            //获取claims
            Claims claims = Jwts.parser()
                    .setSigningKey(key)     //此处的key要与之前创建的key一致
                    .parseClaimsJws(token)
                    .getBody();
            //获取name和level
            name = (String) claims.get("username");
            pwd = (String) claims.get("password");
        }catch (Exception ex){

        }
        if(name==null || pwd==null){
            return false;
        }
        return true;
    }
}
