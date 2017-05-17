var person = new Object();
person.name = 'bblu';
person.age = 35;
person.job = 'software engineer'
person.sayName = function(){
  console.log(this.name);
}

var person = {
  name: 'bblu',
  age: 35,
  job: 'software engineer',
  sayName: function(){
    console.log(this.name);
  }
};
