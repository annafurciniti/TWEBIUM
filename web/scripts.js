var app = new Vue ({
    el: '#app',
    data: {
        logged: false,
        sessione: '',
        ripetizioni: [],
        corsi: [],
        docenti: [],
        utente: {
            username: '',
            password: '',
            amministratore: false
        },
        link: '/TWEBIUM/MyServlet',
        err: null,
        loading: false
    },
    methods: {
        incrementIndex: function (index) {//commit
            return index + 1;
        },
        login: function () {
            var self = this;
            if (self.sessione === '') {
                $.post(this.link, {
                        action: 'LOGIN',
                        username: this.utente.username,
                        password: this.utente.password
                    },
                    function (data) {
                        self.err = null;
                        self.utente = data[0];
                        self.sessione = data[1];

                        if (self.utente.username === "") {
                            self.logged = false;
                            self.err = "Nome utente o password errati."
                        } else {
                            self.logged = true;
                            self.ripetizioni = data[2];
                        }
                    });
            } else {
                $.post(this.link, {
                        action: 'LOGIN',
                        username: this.utente.username,
                        password: this.utente.password,
                        sessione: this.sessione
                    }, function (data) {
                        this.loading = true;
                        self.err = null;
                        self.utente = data[0];
                        self.sessione = data[1];

                        if (self.utente.username === "") {
                            self.logged = false;
                            self.err = "Nome utente o password errati."
                        } else {
                            self.ripetizioni = data[2];
                            self.logged = true;

                        }
                    }
                );
            }
        },
        signin: function () {
            var self=this;

            if (this.sessione === '') {
                $.post(this.link, {
                        action: 'SIGNIN',
                        username: this.utente.username,
                        password: this.utente.password,
                        amministratore: this.utente.amministratore
                    },
                    function (data) {
                        self.utente = data[0];
                        self.sessione = data[1];

                        if (self.utente.username !== "") {
                            self.logged = true;
                            self.ripetizioni = data[2];

                        }
                        else{
                            self.err="Nome Utente già utilizzato"
                        }
                    });
            } else {
                $.post(this.link, {
                        action: 'SIGNIN',
                        username: this.utente.username,
                        password: this.utente.password,
                        amministratore: this.utente.amministratore,
                        sessione: this.sessione
                    },
                    function (data) {
                        self.utente = data[0];
                        self.sessione = data[1];

                        if (self.utente.username !== "") {
                            self.logged = true;
                            self.ripetizioni = data[2];

                        }
                        else{
                            self.err="Nome Utente già utilizzato"
                        }
                    });
            }
        },
        logout:function(){
            $.post(this.link,{
                    action: 'LOGOUT'
                }
            );
            this.logged=false;
            this.sessione="";
        },
        isLogged:function(){
            return this.logged;
        },


    }
});