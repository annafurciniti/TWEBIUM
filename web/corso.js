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
        getCorsi():function(){
            var self =this;
            $.post(this.link, function(data){
                self.corsi =data[3];
            })
        },
        inserisciCorso(): function(){
            var self=this;
            $.post(this.link, {action: 'CORSI',
                    titolo: titolo},
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