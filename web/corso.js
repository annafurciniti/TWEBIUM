var corsi = new Vue ({
    el: '#corsi',
    data: {
        logged: false,
        sessione: '',
        ripetizioni: [],
        corsi: {
            titolo: ''
        },
        corso: {
            titolo: ''
        },
        titoloAggiunto: '',
        docenti: {
            nome: '',
            cognome: ''
        },
        insegnamenti: {
            id_docente: '',
            id_corso: '',
        },
        utente: {
            username: '',
            password: '',
            amministratore: false
        },
        link: '/TWEBIUM/MyServlet',
        err: null,
        loading: false
    },
    mounted() {
        this.getCorsi();
        this.InserisciCorso();
        this.getDocenti();
        this.InserisciDocenti();
        this.getInsegnamenti();
        this.InserisciInsegnamenti();
        this.rimuoviCorso();
        this.rimuoviDocenti();
        this.rimuoviInsegnamenti();
    },
    methods: {
        incrementIndex: function (index) {//commit
            return index + 1;
        },
        getCorsi: function () {
            var self = this;
            $.get(this.link, {
                    action: 'SHOWCORSO',
                    titolo: this.corsi.titolo
                },
                function (data) {
                    self.corsi=data;

                })
        },
        /*aggiungi corso*/
        InserisciCorso: function () {
            var self = this;
            if (self.corsi.titolo !== "") {
            $.post(this.link, {
                    action: 'NEWCORSO',
                    titolo: this.corsi.titolo
                },
                function (data) {
                    self.corsi = data[3]
                });
            } else {
                self.err = "nome gia presente nel db"
            }

        },
        getDocenti: function () {
            var self = this;
            $.get(this.link, {
                    action: 'SHOWDOCENTE',
                    nome: this.docenti.nome,
                    cognome: this.docenti.cognome
                },
                function (data) {
                    self.docenti[6];
                })
        },
        /*aggiungi docenti*/
        InserisciDocenti: function () {
            var self = this;
            if (self.docenti.nome !== "" && self.docenti.cognome !== "") {
                $.post(this.link, {
                        action: 'NEWDOCENTE',
                        nome: this.docenti.nome,
                        cognome: this.docenti.cognome
                    },
                    function (data) {
                        self.docenti = data[6]
                    })
            }else {
                        self.err = "nome gia presente nel db"
                    }

        },

        getInsegnamenti: function () {
            var self = this;
            $.get(this.link, {
                    action: 'SHOWINSEGNAMENTI',
                    id_corso: this.insegnamenti.id_corso,
                    id_docente: this.insegnamenti.id_docente,
                },
                function (data) {
                    self.insegnamenti[7];
                })
        },
        /*aggiungi insegnamenti*/
        InserisciInsegnamenti: function () {
            var self = this;
            $.post(this.link, {
                    action: 'NEWINSEGNAMENTI',
                    id_corso: this.insegnamenti.id_corso,
                    id_docente: this.insegnamenti.id_docente,
                },
                function (data) {
                    self.insegnamenti = data[7]
                    if (self.insegnamenti.id_corso !== "" && self.insegnamenti.id_docente !== "") {
                        self.insegnamenti = data[7]
                    } else {
                        self.err = "nome gia presente nel db"
                    }
                })
        },

        rimuoviCorso: function () {
            var self = this;
            if (self.corsi.titolo !== "") {
                $.post(this.link, {
                        action: 'RIMUOVICORSO',
                        titolo: this.corsi.titolo
                    },
                    function (data) {
                        if (data == "true") {
                            alert("rimosso")
                            window.location.reload(); /*aggiorno la pagina*/
                        } else {
                            self.err = "errore"
                        }
                    });
            }else{
                self.err= "non é stato inserito nessun corso"
            }


        },
        rimuoviDocenti: function () {
            var self = this;
            if (self.docenti.nome !== "" && self.docenti.cognome !== "") {
                $.post(this.link, {
                        action: 'RIMUOVIDOC',
                        nome: this.docenti.nome,
                        cognome: this.docenti.cognome
                    },
                    function (data) {
                        if (data == "true") {
                            alert("rimosso")
                            window.location.reload(); /*aggiorno la pagina*/
                        } else {
                            self.err = "errore"
                        }
                    });
            }else{
                self.err="non é stato inserito alcun docente"
            }


        },
        rimuoviInsegnamenti: function () {
            var self = this;
            $.post(this.link, {action: 'RIMUOVIINSEGNAMENTI',
                   id_corso : this.insegnamenti.id_corso,
                    id_docente:this.insegnamenti.id_docente},
                function (data) {
                    if (data == "true") {
                        alert("rimosso")
                        window.location.reload(); /*aggiorno la pagina*/
                    } else {
                        self.err = "errore"
                    }
                });


        },
        isLogged:function(){
            return this.logged;
        },
    }
});
