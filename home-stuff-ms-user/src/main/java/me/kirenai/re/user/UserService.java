package me.kirenai.re.user;

import java.util.List;

/**
 * UserService
 * @author Kirenai
 */
public interface UserService {

    List<User> findAll();

    User findOne(Long id);

    User create(User data);

    User update(Long id, User data);

    void delete(Long id);

}
