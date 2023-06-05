package day01.ex04;

public class UsersArrayList implements UsersList {
    private User[] users;
    private Integer size;
    private Integer capacity;
    private static final int DEFAULT_SIZE = 10;

    public UsersArrayList() {
        this.users = new User[DEFAULT_SIZE];
        this.size = 0;
        this.capacity = DEFAULT_SIZE;
    }

    @Override
    public void addUser(User user) {
        if (size + 1 >= capacity) {
            capacity = capacity + capacity / 2;
            User[] tmp = new User[capacity];
            for (int i = 0; i < size; i++) {
                tmp[i] = users[i];
            }
            users = tmp;
        }
        users[size] = user;
        ++size;
    }

    @Override
    public User getUserById(int id) {
        for (int i = 0; i < size; i++) {
            if (users[i].getIdentifier() == id) {
                return users[i];
            }
        }
        throw new UserNotFoundException("Invalid id!");
    }

    @Override
    public User getUserByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new UserNotFoundException("Invalid index!");
        }
        return users[index];
    }

    @Override
    public int getNumberOfUsers() {
        return size;
    }

    public Integer getCapacity() {return this.capacity;}
}
