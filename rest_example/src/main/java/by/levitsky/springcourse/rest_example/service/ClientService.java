package by.levitsky.springcourse.rest_example.service;

import by.levitsky.springcourse.rest_example.models.Client;

import java.util.List;

public interface ClientService {
    void create(Client client);

    List<Client> readAll();

    Client read(int id);

    boolean update(Client client, int id);

    boolean delete(int id);
}
