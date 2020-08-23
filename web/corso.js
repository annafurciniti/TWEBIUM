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
    mounted(){
        this.getCorsi();
        this.InserisciCorso();
    },
    methods: {
        incrementIndex: function (index) {//commit
            return index + 1;
        },
        getCorsi: function () {
            var self = this;
            $.post(this.link, {
                action: 'SHOWCORSO',
                titolo: this.corsi.titolo},
                function (data) {
                self.corsi[3];
            })
           /* var i;
            for(i = 0; i < self.corsi.length; i++){
                self.c[i] = self.corsi[i].titolo;
            }*/
        },
        InserisciCorso: function () {
            var self = this;
            $.post(this.link, {action: 'NEWCORSO',
                    titolo: this.corsi.titolo},
                function (data) {
                       self.corsi = data[3]
                    if (self.corsi.titolo !== "") {
                        self.corsi = data[3]
                    } else {
                        self.err = "nome gia presente nel db"
                    }
                })
        }
    }
})
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