package javajo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * セキュリティ設定クラスです.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // CSRF無効
            .csrf().disable()
            // 認証なしの設定
            .authorizeRequests()
                .antMatchers("/", "/login", "/css/**", "/img/**").permitAll()
                .anyRequest().authenticated()
                .and()
            // 認証設定
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/welcome")
                .permitAll()
                .and()
            // ログアウト設定
            .logout()
                .permitAll();
    }

    /**
     * 認証情報を設定する.
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // TODO ユーザを追加する
    }
}
