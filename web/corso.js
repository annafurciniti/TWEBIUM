var corsi = new Vue ({
    el: '#corsi',
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
    methods:{
        incrementIndex: function (index) {//commit
            return index + 1;
        },
        getCorsi :function(){
            var self =this;
            $.post(this.link, function(data){
                self.corsi =data[3];
            })
        },
        inserisciCorso: function(){
            var self=this;
            $.post(this.link, {action: 'NUOVOCORSO'},
                function(data){
                    self.corsi= data[3]
                    if(self.corsi.titolo!=""){
                        self.corsi=data[3]
                    }else{
                        self.err="nome gia presente nel db"
                    }
                })
        }

    }
});