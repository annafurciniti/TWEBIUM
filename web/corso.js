var corsi = new Vue ({
    el: '#corsi',
    data: {
        logged: false,
        sessione: '',
        ripetizioni: [],
        corsi: {
            titolo:''},
        corso: '',
        titoloAggiunto: '',
        docenti: {
            nome:'',
            cognome:''
        },
        insegnamenti:{
            id_docente:'',
            id_corso:'',
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
    mounted(){
        this.getCorsi();
        this.InserisciCorso();
        this.getDocenti();
        this.InserisciDocenti();
        this.getInsegnamenti();
        this.InserisciInsegnamenti();
    },
    methods: {
        incrementIndex: function (index) {//commit
            return index + 1;
        },
        getCorsi: function () {
            var self = this;
            $.post(this.link, {
                    action: 'SHOWCORSO',
                    titolo: this.corsi.titolo
                },
                function (data) {
                    self.corsi[3];
                })
            /* var i;
             for(i = 0; i < self.corsi.length; i++){
                 self.c[i] = self.corsi[i].titolo;
             }*/
        },
        /*aggiungi corso*/
        InserisciCorso: function () {
            var self = this;
            $.post(this.link, {
                    action: 'NEWCORSO',
                    titolo: this.corsi.titolo
                },
                function (data) {
                    self.corsi = data[3]
                    if (self.corsi.titolo !== "") {
                        self.corsi = data[3]
                    } else {
                        self.err = "nome gia presente nel db"
                    }
                })
        },
        getDocenti: function () {
            var self = this;
            $.post(this.link, {
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
            $.post(this.link, {
                    action: 'NEWDOCENTE',
                    nome: this.docenti.nome,
                    cognome: this.docenti.cognome
                },
                function (data) {
                    self.docenti = data[6]
                    if (self.docenti.nome !== "" && self.docenti.cognome !== "") {
                        self.docenti = data[6]
                    } else {
                        self.err = "nome gia presente nel db"
                    }
                })

        },

        getInsegnamenti: function () {
            var self = this;
            $.post(this.link, {
                    action: 'SHOWINSEGNAMENTI',
                    id_corso: this.insegnamenti.id_corso,
                    id_docente: this.insegnamenti.id_docente,
                },
                function (data) {
                    self.insegnamenti[7];
                })
            /* var i;
             for(i = 0; i < self.corsi.length; i++){
                 self.c[i] = self.corsi[i].titolo;
             }*/
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
    },

});
       /* rimuoviCorso: function(){
            var self=this;
            $.post(this.link, {action: 'RIMUOVICORSO', titoloAggiunto: this.titoloAggiunto},
                function(data){
                if(data== "true"){
                    alert("rimosso")
                    window.location.reload(); /*aggiorno la pagina*/
              /*  }else{
                    self.err="errore"
                }
                });


        },
        salvaCorso: function(){
            this.corso= corso;
        },
        modificaCorso: function(){

        }

    }
});*/
