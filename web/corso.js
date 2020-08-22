var corsi = new Vue ({
    el: '#corsi',
    data: {
        logged: false,
        sessione: '',
        ripetizioni: [],
        corsi: {
            id_corso:'',
            titolo:''},
        corso: '',
        titoloAggiunto: '',
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
   /*        getCorsi: function () {
            var self = this;
            $.post(this.link, {
                id_corso: this.id_corso,
                titolo: this.titolo},
                function (data) {
                self.data[3];
            })
            var i;
            for(i = 0; i < self.corsi.length; i++){
                self.c[i] = self.corsi[i].titolo;
            }
        },
    /*    InserisciCorso: function () {
            var self = this;
            $.post(this.link, {action: 'NEWCORSO', id_corso: this.id_corso,
                    titolo: this.titolo,
                    titoloAggiunto: this.titoloAggiunto},
                function (data) {
                    self.corso = data[4]
                    if (self.corsi.titoloAggiunto != "") {
                        self.corsi = data[3]
                    } else {
                        self.err = "nome gia presente nel db"
                    }
                })
        }
    }
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