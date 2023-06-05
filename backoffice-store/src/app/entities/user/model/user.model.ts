export class User {
    id?: number;
    nick?: string;
    nombre?: string;
    apellidos?: string;
    telefono?: number;
    email?: string;
    password?: string;
    sessionId?: string;



constructor(
    id?: number | undefined,
    nick?: string,
    nombre?: string,
    apellidos?: string,
    telefono?: number,
    email?: string,
    password?: string
    ) {
    this.id = id;
    this.nick = nick;
    this.nombre = nombre;
    this.apellidos = apellidos;
    this.telefono = telefono;
    this.email = email;
    this.password = password;
    }
}
