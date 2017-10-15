package com.lzj.domain;

import java.security.Principal;

public final class Client implements Principal {
    private final String name;
    public Client(String name){
        this.name=name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        return getName().equals(client.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public String getName() {
        return name;
    }
}
